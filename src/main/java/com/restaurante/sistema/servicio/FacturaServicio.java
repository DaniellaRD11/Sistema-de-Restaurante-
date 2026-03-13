package com.restaurante.sistema.servicio;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.sistema.dto.FacturaReporteDTO;
import com.restaurante.sistema.dto.FacturaSolicitudDTO;
import com.restaurante.sistema.modelo.DetallePedido;
import com.restaurante.sistema.modelo.Factura;
import com.restaurante.sistema.modelo.Mesa;
import com.restaurante.sistema.modelo.Pedido;
import com.restaurante.sistema.repositorio.DetallePedidoRepositorio;
import com.restaurante.sistema.repositorio.FacturaRepositorio;
import com.restaurante.sistema.repositorio.MesaRepositorio;
import com.restaurante.sistema.repositorio.PedidoRepositorio;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class FacturaServicio {

    @Autowired
    private FacturaRepositorio facturaRepository;
    @Autowired
    private PedidoRepositorio pedidoRepository;
    @Autowired
    private DetallePedidoRepositorio detallePedidoRepository;
    @Autowired
    private MesaRepositorio mesaRepository; // ¡Importante para liberar la mesa!

    // --- GENERAR FACTURA (COBRAR) ---
    @Transactional
    public Factura generarFactura(FacturaSolicitudDTO solicitud) {
        
        // 1. Validar Pedido
        Pedido pedido = pedidoRepository.findById(solicitud.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // 2. Validar Detalles
        List<DetallePedido> detallesAPagar = detallePedidoRepository.findAllById(solicitud.getDetallesIds());
        if (detallesAPagar.isEmpty()) {
            throw new RuntimeException("No hay platos seleccionados para pagar.");
        }

        // 3. Configurar Cabecera Factura
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setFechaFactura(LocalDateTime.now());
        factura.setMetodoPago(solicitud.getMetodoPago());
        factura.setEstadoFactura(Factura.EstadoFactura.PAGADA); // Usando el ENUM

        // 4. Calcular Totales
        double subtotal = 0.0;
        for (DetallePedido detalle : detallesAPagar) {
            if (detalle.getFactura() != null) {
                throw new RuntimeException("El plato '" + detalle.getPlato().getNombre() + "' ya está pagado.");
            }
            subtotal += detalle.getSubtotal();
            detalle.setFactura(factura); // Vincular detalle a factura
        }

        double propina = subtotal * 0.10;
        double total = subtotal + propina;

        // Llenar campos numéricos para evitar NULLs
        factura.setSubtotalBase(subtotal);
        factura.setPropina(propina);
        factura.setTotal(total);

        // 5. Guardar en BD
        factura = facturaRepository.save(factura);
        detallePedidoRepository.saveAll(detallesAPagar);

        // 6. Verificar si cerramos Pedido y Liberamos Mesa
        boolean todoPagado = true;
        List<DetallePedido> todosDetalles = detallePedidoRepository.findByPedido(pedido);
        
        for (DetallePedido d : todosDetalles) {
            if (d.getFactura() == null) {
                todoPagado = false;
                break;
            }
        }

        if (todoPagado) {
            // Cerrar Pedido
            pedido.setEstado(Pedido.EstadoPedido.CERRADO);
            pedidoRepository.save(pedido);

            // Liberar Mesa
            Mesa mesa = pedido.getMesa();
            if (mesa != null) {
                mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
                mesaRepository.save(mesa);
            }
        }

        return factura;
    }

    // --- GENERAR PDF ---
    public byte[] generarReportePdf(Integer facturaId) throws Exception {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // Mapear a DTO
        List<FacturaReporteDTO> listaDatos = new ArrayList<>();
        Date fechaDate = Date.from(factura.getFechaFactura().atZone(ZoneId.systemDefault()).toInstant());

        for (DetallePedido det : factura.getPedido().getDetalles()) {
            // Solo los platos de ESTA factura
            if (det.getFactura() != null && det.getFactura().getFacturaId().equals(facturaId)) {
                FacturaReporteDTO fila = new FacturaReporteDTO(
                    factura.getFacturaId(),
                    fechaDate,
                    det.getPlato().getNombre(),
                    det.getCantidad(),
                    det.getPlato().getPrecio(),
                    factura.getPedido().getMesa().getNumeroMesa(),
                    factura.getTotal(),
                    factura.getEstadoFactura().toString()
                );
                listaDatos.add(fila);
            }
        }

        // Cargar JRXML de forma robusta
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/FacturaProducto.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException("No se encontró el archivo /reports/FacturaProducto.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDatos);
        Map<String, Object> parameters = new HashMap<>();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
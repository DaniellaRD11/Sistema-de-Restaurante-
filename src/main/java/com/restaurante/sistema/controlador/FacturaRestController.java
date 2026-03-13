package com.restaurante.sistema.controlador;

import com.restaurante.sistema.dto.FacturaSolicitudDTO;
import com.restaurante.sistema.modelo.Factura;
import com.restaurante.sistema.servicio.FacturaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
public class FacturaRestController {

    @Autowired
    private FacturaServicio facturaServicio;

    // 1. Generar Factura (Cobrar)
    @PostMapping("/generar")
    public ResponseEntity<?> generarFactura(@RequestBody FacturaSolicitudDTO solicitud) {
        try {
            Factura nuevaFactura = facturaServicio.generarFactura(solicitud);
            return ResponseEntity.ok(nuevaFactura);
        } catch (Exception e) {
            e.printStackTrace(); // Para ver errores en consola
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 2. Descargar PDF
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Integer id) {
        try {
            byte[] reportePdf = facturaServicio.generarReportePdf(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // "inline" para abrir en navegador, "attachment" para descargar
            headers.setContentDispositionFormData("inline", "Factura_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportePdf);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
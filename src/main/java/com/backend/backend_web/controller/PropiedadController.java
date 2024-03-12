    package com.backend.backend_web.controller;

    import java.util.List;
    import java.util.UUID;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.CrossOrigin;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import com.backend.backend_web.dto.PropiedadDTO;
    import com.backend.backend_web.service.PropiedadService;

    @RequestMapping("/propiedad")
    @RestController
    public class PropiedadController {

        @Autowired
        private PropiedadService service;

        @CrossOrigin
        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<PropiedadDTO> createPropiedad(@RequestBody PropiedadDTO propiedad) {
            try {
                if (propiedad == null) {
                    return ResponseEntity.badRequest().build();
                }
                PropiedadDTO savedPropiedad = service.save(propiedad);
                return ResponseEntity.ok().body(savedPropiedad);
            } catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        }

        @CrossOrigin
        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Iterable<PropiedadDTO>> readAllPropiedad() {
            return ResponseEntity.ok().body(service.get());
        }

        @CrossOrigin
        @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<PropiedadDTO> updatePropiedad(@PathVariable UUID id, @RequestBody PropiedadDTO propiedad) {
            try {
                if (propiedad == null) {
                    return ResponseEntity.badRequest().build();
                }
                PropiedadDTO updatedPropiedad = service.update(propiedad);
                return ResponseEntity.ok().body(updatedPropiedad);
            } catch (Exception e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        }

        @CrossOrigin
        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> deletePropiedad(@PathVariable UUID id) {
            service.delete(id);
            return ResponseEntity.ok().build();
        }

    }

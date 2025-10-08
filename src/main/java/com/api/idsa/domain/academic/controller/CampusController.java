package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.CampusRequest;
import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.service.ICampusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/admin/campuses")
@Tag(
	name = "Planteles",
	description = "Endpoints para la gestión de planteles educativos. Permite crear, consultar, actualizar y eliminar planteles."
)
public class CampusController {

    @Autowired
    ICampusService campusService;

    @Operation(
        summary = "Obtener todos los planteles",
        description = "Retorna una lista paginada de todos los planteles educativos registrados. " +
                      "Permite búsqueda por nombre del plantel."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<CampusResponse>>> getAllCampus(
        @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
        @Parameter(description = "Término de búsqueda para filtrar por nombre") @RequestParam(required = false) String search
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page); 
        Page<CampusResponse> campusPage = campusService.getAllCampus(pageable, search);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<List<CampusResponse>>(
                HttpStatus.OK,
                "Campus retrieved successfully",
                campusPage.getContent(),
                PageInfo.fromPage(campusPage)
            )
        );
    }

    @Operation(
        summary = "Crear un nuevo plantel",
        description = "Crea un nuevo plantel educativo con la información proporcionada. " +
                      "El nombre del plantel debe ser único."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CampusResponse>> createCampus(@Valid @RequestBody CampusRequest campusRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse<CampusResponse>(
                HttpStatus.CREATED,
                "Campus created successfully",
                campusService.createCampus(campusRequest)
            )
        );
    }

    @Operation(
        summary = "Actualizar un plantel",
        description = "Actualiza la información de un plantel educativo existente por su ID. " +
                      "El plantel debe existir en el sistema."
    )
    @PutMapping("/{campusId}")
    public ResponseEntity<ApiResponse<CampusResponse>> updateCampus(
        @Parameter(description = "ID único del plantel a actualizar") @PathVariable UUID campusId,
        @Valid @RequestBody CampusRequest campusRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<CampusResponse>(
                HttpStatus.OK,
                "Campus updated successfully",
                campusService.updateCampus(campusId, campusRequest)
            )
        );
    }

    @Operation(
        summary = "Eliminar un plantel",
        description = "Elimina un plantel educativo del sistema por su ID. " +
                      "El plantel no debe tener configuraciones de grupo asociadas."
    )
    @DeleteMapping("/{campusId}")
    public void deleteCampus(
        @Parameter(description = "ID único del plantel a eliminar") @PathVariable UUID campusId) {
        campusService.deleteCampus(campusId);
    }

}

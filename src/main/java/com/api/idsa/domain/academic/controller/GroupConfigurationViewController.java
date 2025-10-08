package com.api.idsa.domain.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationViewResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationViewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/common/group-configurations-view")
@Tag(
	name = "Vista de Configuraciones de Grupo",
	description = "Endpoint de solo lectura para consultar configuraciones de grupo con información simplificada y concatenada. " +
	              "Ideal para listados y selección de grupos."
)
public class GroupConfigurationViewController {

	@Autowired
	IGroupConfigurationViewService groupConfigurationViewService;

	@Operation(
		summary = "Obtener vista simplificada de configuraciones de grupo",
		description = "Retorna una lista paginada de configuraciones de grupo con información concatenada y simplificada. " +
		              "Incluye el nombre del tutor, plantel, especialidad, modalidad, grado, grupo, generación y cantidad de estudiantes. " +
		              "Permite búsqueda por cualquier campo visible."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<GroupConfigurationViewResponse>>> getAllGroupConfigurationView(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda para filtrar por cualquier campo") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<GroupConfigurationViewResponse> groupConfigViewPage = groupConfigurationViewService.getAllGroupConfigurationView(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<GroupConfigurationViewResponse>>(
				HttpStatus.OK,
				"Group configuration views retrieved successfully",
				groupConfigViewPage.getContent(),
				PageInfo.fromPage(groupConfigViewPage)
			)
		);
	}

}

package aluno_ac2.ac2_ca.controller;

import aluno_ac2.ac2_ca.dto.AlunoCreateDTO;
import aluno_ac2.ac2_ca.dto.AlunoResponseDTO;
import aluno_ac2.ac2_ca.dto.UpdateMediaDTO;
import aluno_ac2.ac2_ca.service.AlunoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    // 1) Criar aluno
    @PostMapping
    public AlunoResponseDTO criarAluno(@RequestBody AlunoCreateDTO dto) {
        return alunoService.criarAluno(dto);
    }

    // 2) Buscar aluno por ID
    @GetMapping("/{id}")
    public AlunoResponseDTO buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    // 3) Listar todos os alunos
    @GetMapping
    public List<AlunoResponseDTO> listarTodos() {
        return alunoService.listarTodos();
    }

    // 4) Atualizar média do aluno
    //    Usa o DTO real: UpdateMediaDTO(double media)
    @PutMapping("/{id}/media")
    public AlunoResponseDTO atualizarMedia(
            @PathVariable Long id,
            @RequestBody UpdateMediaDTO dto
    ) {
        return alunoService.atualizarMedia(id, dto);
    }

    // 5) Liberar cursos (DISPARA RabbitMQ + IA)
    @PostMapping("/{id}/liberar-cursos")
    public AlunoResponseDTO liberarCursos(@PathVariable Long id) {
        return alunoService.liberarCursos(id);
    }

    // 6) Verificar plano (usa regra de negócio do service)
    @PostMapping("/{id}/verificar-plano")
    public AlunoResponseDTO verificarPlano(@PathVariable Long id) {
        return alunoService.verificarPlano(id);
    }
}

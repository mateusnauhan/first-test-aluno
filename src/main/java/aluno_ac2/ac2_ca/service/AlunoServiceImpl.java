package aluno_ac2.ac2_ca.service;

import aluno_ac2.ac2_ca.dto.*;
import aluno_ac2.ac2_ca.entity.Aluno;
import aluno_ac2.ac2_ca.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import aluno_ac2.ac2_ca.infrastructure.messaging.event.CursoConcluidoEvent;
import aluno_ac2.ac2_ca.infrastructure.messaging.publisher.CursoConcluidoEventPublisher;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoConcluidoEventPublisher eventPublisher;

    public AlunoServiceImpl(AlunoRepository alunoRepository,CursoConcluidoEventPublisher eventPublisher) {
        this.alunoRepository = alunoRepository;
        this.eventPublisher = eventPublisher;
    }

    private Aluno getAluno(Long id) {
        return alunoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    @Override
    @Transactional
    public AlunoResponseDTO criarAluno(AlunoCreateDTO dto) {
        Aluno aluno = new Aluno(dto.nome(), dto.email(), dto.plano());
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return AlunoResponseDTO.fromEntity(alunoSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public AlunoResponseDTO buscarPorId(Long id) {
        return AlunoResponseDTO.fromEntity(getAluno(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AlunoResponseDTO atualizarMedia(Long id, UpdateMediaDTO dto) {
        Aluno aluno = getAluno(id);
        aluno.setMediaFinal(dto.media());
        return AlunoResponseDTO.fromEntity(alunoRepository.save(aluno));
    }

    @Override
    @Transactional
    public AlunoResponseDTO verificarPlano(Long id) {
        Aluno aluno = getAluno(id);
        aluno.verificarPlano();
        return AlunoResponseDTO.fromEntity(alunoRepository.save(aluno));
    }

    @Override
    @Transactional
    public AlunoResponseDTO liberarCursos(Long id) {
        Aluno aluno = getAluno(id);
        aluno.liberarCursos();
        Aluno salvo = alunoRepository.save(aluno);

        CursoConcluidoEvent event = CursoConcluidoEvent.from(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getMediaFinal().value(),
                salvo.getCursosConcluidos().value(),
                salvo.getCursosLiberados().value(),
                salvo.getPlano().asLabel()
        );

        // Publica no RabbitMQ
        eventPublisher.publish(event);

        return AlunoResponseDTO.fromEntity(salvo);
    }
}
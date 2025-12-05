package aluno_ac2.ac2_ca.infrastructure.messaging.event;

import java.time.LocalDateTime;

/**
   Evento de domínio que representa o progresso/conclusão do aluno.
 */
public record CursoConcluidoEvent(
        Long alunoId,
        String alunoNome,
        String alunoEmail,
        Double mediaFinal,
        Integer cursosConcluidos,
        Integer cursosLiberados,
        String plano,
        LocalDateTime dataProcessamento
) {
    public static CursoConcluidoEvent from(
            Long alunoId,
            String alunoNome,
            String alunoEmail,
            Double mediaFinal,
            Integer cursosConcluidos,
            Integer cursosLiberados,
            String plano
    ) {
        return new CursoConcluidoEvent(
                alunoId,
                alunoNome,
                alunoEmail,
                mediaFinal,
                cursosConcluidos,
                cursosLiberados,
                plano,
                LocalDateTime.now()
        );
    }
}

package br.edu.unifacisa.impacta;

import br.edu.unifacisa.impacta.model.Curso;
import br.edu.unifacisa.impacta.model.Estudante;
import br.edu.unifacisa.impacta.repository.CursoRepository;
import br.edu.unifacisa.impacta.repository.EstudanteRepository;
import br.edu.unifacisa.impacta.service.EstudanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudanteServiceTest {

    @Mock
    private EstudanteRepository estudanteRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private EstudanteService estudanteService;

    private Estudante estudante;
    private Curso curso;

    @BeforeEach
    void setUp() {
        estudante = new Estudante("João Silva", "joao@email.com");
        estudante.setId(1L);

        curso = new Curso("Spring Boot");
        curso.setId(1L);
    }

    @Test
    void deveListarTodosOsEstudantes() {
        when(estudanteRepository.findAll()).thenReturn(List.of(estudante));

        List<Estudante> resultado = estudanteService.listarTodos();

        assertEquals(1, resultado.size());
        verify(estudanteRepository).findAll();
    }

    @Test
    void deveBuscarEstudantePorId() {
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));

        Optional<Estudante> resultado = estudanteService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
    }

    @Test
    void deveCriarEstudanteComSucesso() {
        when(estudanteRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(estudanteRepository.save(any(Estudante.class))).thenReturn(estudante);

        Estudante resultado = estudanteService.criar(estudante);

        assertNotNull(resultado);
        verify(estudanteRepository).save(any(Estudante.class));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExistir() {
        when(estudanteRepository.existsByEmail("joao@email.com")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            estudanteService.criar(estudante);
        });

        verify(estudanteRepository, never()).save(any());
    }

    @Test
    void deveMatricularEstudanteEmUmCourse() {
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(estudanteRepository.save(any(Estudante.class))).thenReturn(estudante);

        Estudante resultado = estudanteService.matricular(1L, 1L);

        verify(estudanteRepository).save(any(Estudante.class));
        verify(cursoRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoMatricularEstudanteJaMatriculado() {
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        estudante.matricular(curso); // já matricula

        assertThrows(IllegalStateException.class, () -> {
            estudanteService.matricular(1L, 1L);
        });
    }

    @Test
    void deveDesmatricularEstudanteDeUmCourse() {
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(estudanteRepository.save(any(Estudante.class))).thenReturn(estudante);

        estudante.matricular(curso); // matricula primeiro
        estudanteService.desmatricular(1L, 1L);

        verify(estudanteRepository).save(any(Estudante.class));
    }

    @Test
    void deveLancarExcecaoAoDesmatricularEstudanteNaoMatriculado() {
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        assertThrows(IllegalStateException.class, () -> {
            estudanteService.desmatricular(1L, 1L);
        });
    }

    @Test
    void deveAtualizarEstudante() {
        Estudante atualizado = new Estudante("Maria Santos", "maria@email.com");
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        when(estudanteRepository.existsByEmailAndIdNot("maria@email.com", 1L)).thenReturn(false);
        when(estudanteRepository.save(any(Estudante.class))).thenReturn(estudante);

        estudanteService.atualizar(1L, atualizado);

        verify(estudanteRepository).save(any(Estudante.class));
    }

    @Test
    void deveDeletarEstudante() {
        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));
        doNothing().when(estudanteRepository).delete(estudante);

        estudanteService.deletar(1L);

        verify(estudanteRepository).delete(estudante);
    }
}

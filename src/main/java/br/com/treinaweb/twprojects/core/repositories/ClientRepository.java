package br.com.treinaweb.twprojects.core.repositories;

import br.com.treinaweb.twprojects.core.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository  extends JpaRepository<Client, Long> {
}

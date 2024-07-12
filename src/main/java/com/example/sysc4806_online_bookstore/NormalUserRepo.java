package com.example.sysc4806_online_bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalUserRepo extends JpaRepository<NormalUser, Integer> {
    NormalUser findById(long id);

    NormalUser findByUsername(String username);
}
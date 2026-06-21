-- =========================================
-- Database: db_pokemon
-- Tema: Pokemon Trainer Management System
-- =========================================

CREATE DATABASE IF NOT EXISTS db_pokemon;
USE db_pokemon;

-- 1. Tabel User (Login)
CREATE TABLE tb_user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    nama_lengkap VARCHAR(100)
);

-- 2. Tabel Trainer
CREATE TABLE tb_trainer (
    id_trainer INT AUTO_INCREMENT PRIMARY KEY,
    nama_trainer VARCHAR(100) NOT NULL,
    kota_asal VARCHAR(100),
    tanggal_daftar DATE
);

-- 3. Tabel Pokemon (berelasi ke trainer)
CREATE TABLE tb_pokemon (
    id_pokemon INT AUTO_INCREMENT PRIMARY KEY,
    nama_pokemon VARCHAR(100) NOT NULL,
    tipe VARCHAR(50),
    level INT DEFAULT 1,
    id_trainer INT,
    FOREIGN KEY (id_trainer) REFERENCES tb_trainer(id_trainer)
        ON DELETE CASCADE
);

-- Data awal
INSERT INTO tb_user (username, password, nama_lengkap)
VALUES ('admin', 'admin123', 'Administrator');

INSERT INTO tb_trainer (nama_trainer, kota_asal, tanggal_daftar) VALUES
('Ash Ketchum', 'Pallet Town', '2026-01-10'),
('Misty', 'Cerulean City', '2026-02-15');

INSERT INTO tb_pokemon (nama_pokemon, tipe, level, id_trainer) VALUES
('Pikachu', 'Electric', 25, 1),
('Charizard', 'Fire/Flying', 36, 1),
('Starmie', 'Water/Psychic', 30, 2);

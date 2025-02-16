-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 16 fév. 2025 à 18:32
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `banque_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `email`, `nom`, `telephone`) VALUES
(1, 'jean.dupont@limayrac.fr', 'Jean Dupont', '0601020304'),
(2, 'marie.martin@limayrac.fr', 'Marie Martin', '0605060708'),
(3, 'paul.lefevre@outlook.fr', 'Paul Lefevre', '0612345679'),
(4, 'sophie.durand@yahoo.com', 'Sophie Durand', '0622334455'),
(7, 'mateus.farias@limayrac.fr', 'Mateus FARIAS', '0606060606');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `decouvert_autorise` double NOT NULL,
  `solde` double NOT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`decouvert_autorise`, `solde`, `client_id`, `id`) VALUES
(100, -150, 1, 1),
(200, 2200, 2, 2),
(1000, 2100, 3, 3),
(100, -100, 4, 4),
(500, 1400, 1, 7),
(500, 1600, 1, 8),
(100, -100, 7, 12);

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `montant` double NOT NULL,
  `compte_destinataire_id` bigint(20) DEFAULT NULL,
  `compte_emetteur_id` bigint(20) DEFAULT NULL,
  `date_transaction` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `autorise` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `transaction`
--

INSERT INTO `transaction` (`montant`, `compte_destinataire_id`, `compte_emetteur_id`, `date_transaction`, `id`, `autorise`) VALUES
(100, 3, 2, '2025-02-16 16:44:58.000000', 19, b'1'),
(100, 2, 2, '2025-02-16 16:45:41.000000', 20, b'1'),
(200, NULL, 1, '2025-02-16 16:56:59.000000', 27, b'1'),
(100, 8, 7, '2025-02-16 17:20:29.000000', 28, b'1'),
(100, 12, 2, '2025-02-16 17:21:10.000000', 29, b'1'),
(100, NULL, 12, '2025-02-16 17:22:22.000000', 30, b'1'),
(100, NULL, 12, '2025-02-16 17:22:29.000000', 31, b'1');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5qv5tcfmwu2mli0brm27y13gl` (`client_id`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmdiwmxky5ln0y2dxwnr85eld8` (`compte_destinataire_id`),
  ADD KEY `FKk9oseagh5w38m5j4rjub7g8da` (`compte_emetteur_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `FK5qv5tcfmwu2mli0brm27y13gl` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FKk9oseagh5w38m5j4rjub7g8da` FOREIGN KEY (`compte_emetteur_id`) REFERENCES `compte` (`id`),
  ADD CONSTRAINT `FKmdiwmxky5ln0y2dxwnr85eld8` FOREIGN KEY (`compte_destinataire_id`) REFERENCES `compte` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

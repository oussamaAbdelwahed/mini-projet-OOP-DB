-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Client :  localhost:3306
-- Généré le :  Mar 17 Mars 2020 à 23:47
-- Version du serveur :  5.7.29-0ubuntu0.18.04.1
-- Version de PHP :  7.2.24-0ubuntu0.18.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `mini_projet`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `id` bigint(20) NOT NULL,
  `continent` enum('EUR','AME','AFR','ASI','AUS') NOT NULL,
  `pays` varchar(30) NOT NULL,
  `gouvernorat` varchar(40) NOT NULL,
  `ville` varchar(40) NOT NULL,
  `rue` varchar(60) NOT NULL,
  `codePostal` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `agence`
--

CREATE TABLE `agence` (
  `id` bigint(20) NOT NULL,
  `adresse` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `carte`
--

CREATE TABLE `carte` (
  `numero` bigint(20) NOT NULL,
  `codeInternet` int(11) NOT NULL,
  `codeDab` int(11) NOT NULL,
  `valableJusqua` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `compte` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `id` bigint(20) NOT NULL,
  `solde` double NOT NULL,
  `dateCreation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateFermeture` timestamp NULL DEFAULT NULL,
  `estValable` bit(1) NOT NULL DEFAULT b'1',
  `seuil` double NOT NULL,
  `TYPE` enum('COURANT','EPARGNE') NOT NULL,
  `client` bigint(20) NOT NULL,
  `guichetier` bigint(20) NOT NULL,
  `carte` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `guichet`
--

CREATE TABLE `guichet` (
  `id` bigint(20) NOT NULL,
  `agence` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `operation`
--

CREATE TABLE `operation` (
  `id` int(11) NOT NULL,
  `type` enum('VER','RET','VIR') NOT NULL,
  `dateExec` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `compteSource` bigint(20) NOT NULL,
  `compteDestination` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id` bigint(20) NOT NULL,
  `cin` bigint(20) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaiss` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `salaire` double DEFAULT NULL,
  `dateEmbauche` timestamp NULL DEFAULT NULL,
  `TYPE` enum('GUICHETIER','CLIENT') NOT NULL,
  `guichet` bigint(20) DEFAULT NULL,
  `tel` varchar(13) NOT NULL,
  `sex` enum('MAL','FEM') NOT NULL DEFAULT 'MAL',
  `etatCivil` enum('MAR','DIV','SING') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `agence`
--
ALTER TABLE `agence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `adresse` (`adresse`);

--
-- Index pour la table `carte`
--
ALTER TABLE `carte`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `compte` (`compte`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client` (`client`),
  ADD KEY `guichetier` (`guichetier`),
  ADD KEY `carte` (`carte`);

--
-- Index pour la table `guichet`
--
ALTER TABLE `guichet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `agence` (`agence`);

--
-- Index pour la table `operation`
--
ALTER TABLE `operation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `compteSource` (`compteSource`),
  ADD KEY `compteDestination` (`compteDestination`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cin` (`cin`),
  ADD KEY `guichet` (`guichet`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `agence`
--
ALTER TABLE `agence`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `guichet`
--
ALTER TABLE `guichet`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `operation`
--
ALTER TABLE `operation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `agence`
--
ALTER TABLE `agence`
  ADD CONSTRAINT `agence_ibfk_1` FOREIGN KEY (`adresse`) REFERENCES `adresse` (`id`);

--
-- Contraintes pour la table `carte`
--
ALTER TABLE `carte`
  ADD CONSTRAINT `carte_ibfk_1` FOREIGN KEY (`compte`) REFERENCES `compte` (`id`);

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `compte_ibfk_1` FOREIGN KEY (`carte`) REFERENCES `carte` (`numero`),
  ADD CONSTRAINT `compte_ibfk_2` FOREIGN KEY (`guichetier`) REFERENCES `personne` (`id`),
  ADD CONSTRAINT `compte_ibfk_3` FOREIGN KEY (`id`) REFERENCES `operation` (`compteDestination`);

--
-- Contraintes pour la table `guichet`
--
ALTER TABLE `guichet`
  ADD CONSTRAINT `guichet_ibfk_1` FOREIGN KEY (`agence`) REFERENCES `agence` (`id`);

--
-- Contraintes pour la table `operation`
--
ALTER TABLE `operation`
  ADD CONSTRAINT `operation_ibfk_1` FOREIGN KEY (`compteSource`) REFERENCES `compte` (`id`);

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `personne_ibfk_1` FOREIGN KEY (`guichet`) REFERENCES `guichet` (`id`),
  ADD CONSTRAINT `personne_ibfk_2` FOREIGN KEY (`id`) REFERENCES `compte` (`client`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

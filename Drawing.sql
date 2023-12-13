-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: db:3306
-- Temps de generació: 08-12-2023 a les 22:51:40
-- Versió del servidor: 5.7.44
-- Versió de PHP: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `Drawing`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `draw`
--

CREATE TABLE `draw` (
  `id` int(11) NOT NULL,
  `nameDraw` varchar(100) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `creationDate` datetime NOT NULL,
  `visualization` tinyint(1) DEFAULT '0',
  `inTheTrash` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Bolcament de dades per a la taula `draw`
--

INSERT INTO `draw` (`id`, `nameDraw`, `owner_id`, `creationDate`, `visualization`, `inTheTrash`) VALUES
(145, 'pepe', 2, '2023-12-08 22:01:26', 1, 1),
(146, 'Copia image_6805d930-9f0e-4af3-8123-40f6f2140abb', 3, '2023-12-08 22:07:36', 0, 0),
(147, 'yy', 2, '2023-12-08 22:07:48', 0, 0),
(148, 'Copia image_0afbb93d-85ef-484b-b945-7ab73448c598', 3, '2023-12-08 22:18:15', 0, 0);

-- --------------------------------------------------------

--
-- Estructura de la taula `permissions`
--

CREATE TABLE `permissions` (
  `id_draw` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `permissions` varchar(10) COLLATE dec8_bin NOT NULL DEFAULT 'R',
  `in_your_trash` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=dec8 COLLATE=dec8_bin;

--
-- Bolcament de dades per a la taula `permissions`
--

INSERT INTO `permissions` (`id_draw`, `id_user`, `permissions`, `in_your_trash`) VALUES
(147, 3, 'RW', 0);

-- --------------------------------------------------------

--
-- Estructura de la taula `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `login` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Bolcament de dades per a la taula `user`
--

INSERT INTO `user` (`id`, `login`, `name`, `password`) VALUES
(2, 'aa', 'aa', '827CCB0EEA8A706C4C34A16891F84E7B'),
(3, 'pepe', 'pepe', '827CCB0EEA8A706C4C34A16891F84E7B'),
(4, 'uu', 'uu', '827CCB0EEA8A706C4C34A16891F84E7B');

-- --------------------------------------------------------

--
-- Estructura de la taula `version`
--

CREATE TABLE `version` (
  `id` int(11) NOT NULL,
  `id_draw` int(11) DEFAULT NULL,
  `figures` text COLLATE dec8_bin,
  `numFigures` int(11) NOT NULL,
  `modificationDate` datetime DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=dec8 COLLATE=dec8_bin;

--
-- Bolcament de dades per a la taula `version`
--

INSERT INTO `version` (`id`, `id_draw`, `figures`, `numFigures`, `modificationDate`, `id_user`) VALUES
(124, 145, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":156,\"y\":109}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":247,\"y\":211}]}]', 2, '2023-12-08 22:01:26', 2),
(125, 145, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":156,\"y\":109}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"68\",\"filled\":true,\"coordinates\":[{\"x\":247,\"y\":211}]}]', 2, '2023-12-08 22:06:36', 2),
(126, 146, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":156,\"y\":109}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":247,\"y\":211}]}]', 2, '2023-12-08 22:07:36', 3),
(127, 147, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":145,\"y\":98}]}]', 1, '2023-12-08 22:07:48', 2),
(128, 148, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":145,\"y\":98}]}]', 1, '2023-12-08 22:18:15', 3),
(129, 147, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":145,\"y\":98}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":226,\"y\":110}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":235,\"y\":217}]}]', 3, '2023-12-08 22:22:18', 2),
(130, 148, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":145,\"y\":98}]},{\"type\":\"circle\",\"color\":\"#000000\",\"size\":\"20\",\"filled\":false,\"coordinates\":[{\"x\":288,\"y\":95}]}]', 2, '2023-12-08 22:23:48', 3),
(131, 147, '[{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":145,\"y\":98}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":226,\"y\":110}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":235,\"y\":217}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":298,\"y\":80}]},{\"type\":\"circle\",\"color\":\"#1c33a6\",\"size\":\"29\",\"filled\":false,\"coordinates\":[{\"x\":374,\"y\":149}]}]', 5, '2023-12-08 22:34:44', 2);

--
-- Índexs per a les taules bolcades
--

--
-- Índexs per a la taula `draw`
--
ALTER TABLE `draw`
  ADD PRIMARY KEY (`id`),
  ADD KEY `owner_id` (`owner_id`) USING BTREE;

--
-- Índexs per a la taula `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id_draw`,`id_user`),
  ADD KEY `id_user` (`id_user`);

--
-- Índexs per a la taula `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Índexs per a la taula `version`
--
ALTER TABLE `version`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_draw` (`id_draw`);

--
-- AUTO_INCREMENT per les taules bolcades
--

--
-- AUTO_INCREMENT per la taula `draw`
--
ALTER TABLE `draw`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=149;

--
-- AUTO_INCREMENT per la taula `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la taula `version`
--
ALTER TABLE `version`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=132;

--
-- Restriccions per a les taules bolcades
--

--
-- Restriccions per a la taula `draw`
--
ALTER TABLE `draw`
  ADD CONSTRAINT `draw_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

--
-- Restriccions per a la taula `permissions`
--
ALTER TABLE `permissions`
  ADD CONSTRAINT `permissions_ibfk_1` FOREIGN KEY (`id_draw`) REFERENCES `draw` (`id`),
  ADD CONSTRAINT `permissions_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Restriccions per a la taula `version`
--
ALTER TABLE `version`
  ADD CONSTRAINT `version_ibfk_1` FOREIGN KEY (`id_draw`) REFERENCES `draw` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

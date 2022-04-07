# business-app-development-project
Project for Business Application Development Course, third semester. The project is about Janji-Jywa Application. Janji Jywa is a simple JBDC and MySQL application that manages all the transaction and the system of the beverage. Admins in this application could manage the inventory of the beverages, while the customers could buy the beverages.

Before running the java project file, please establish a MySQL server connection with the java project using XAMPP by making a MySQL database named 'janji_jywa'. After that, please run the following syntax code in the MySQL query:

-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2021 at 08:41 AM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `janji_jywa`
--

-- --------------------------------------------------------

--
-- Table structure for table `beverages`
--

CREATE TABLE `beverages` (
  `BeverageID` char(5) DEFAULT NULL,
  `BeverageName` varchar(30) DEFAULT NULL,
  `BeverageType` varchar(30) DEFAULT NULL,
  `BeveragePrice` int(11) DEFAULT NULL,
  `BeverageStock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `beverages`
--

INSERT INTO `beverages` (`BeverageID`, `BeverageName`, `BeverageType`, `BeveragePrice`, `BeverageStock`) VALUES
('BE001', 'Boba Ashiap', 'Coffee', 10000, 10),
('BE002', 'Es teh manis', 'Tea', 12000, 97),
('BE003', 'Mango smoothie', 'Smoothies', 20000, 100),
('BE004', 'Boba kocak', 'Boba', 19000, 118);

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `UserID` char(5) NOT NULL,
  `BeverageID` char(5) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransactions`
--

CREATE TABLE `detailtransactions` (
  `TransactionID` char(5) NOT NULL,
  `BeverageID` char(5) NOT NULL,
  `Quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailtransactions`
--

INSERT INTO `detailtransactions` (`TransactionID`, `BeverageID`, `Quantity`) VALUES
('TR001', 'BE001', 22),
('TR002', 'BE002', 3),
('TR002', 'BE004', 2);

-- --------------------------------------------------------

--
-- Table structure for table `headertransactions`
--

CREATE TABLE `headertransactions` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) DEFAULT NULL,
  `TransactionDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `headertransactions`
--

INSERT INTO `headertransactions` (`TransactionID`, `UserID`, `TransactionDate`) VALUES
('TR001', 'US002', '2021-05-28'),
('TR002', 'US002', '2021-05-28');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` char(5) DEFAULT NULL,
  `UserName` varchar(30) DEFAULT NULL,
  `UserEmail` varchar(50) DEFAULT NULL,
  `UserPassword` varchar(30) DEFAULT NULL,
  `UserDOB` date DEFAULT NULL,
  `UserGender` varchar(10) DEFAULT NULL,
  `UserAddress` varchar(255) DEFAULT NULL,
  `UserPhone` varchar(30) DEFAULT NULL,
  `UserRole` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserDOB`, `UserGender`, `UserAddress`, `UserPhone`, `UserRole`) VALUES
('US001', 'Revaldi Mijaya', 'admin', 'admin', NULL, 'Male', 'asdasdasdasd Street', '0920398193812319', 'Admin'),
('US002', 'daniel fujiono', 'customer', 'customer', NULL, 'Male', 'binus Street', '012345678911', 'Customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`UserID`,`BeverageID`);

--
-- Indexes for table `detailtransactions`
--
ALTER TABLE `detailtransactions`
  ADD PRIMARY KEY (`TransactionID`,`BeverageID`);

--
-- Indexes for table `headertransactions`
--
ALTER TABLE `headertransactions`
  ADD PRIMARY KEY (`TransactionID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

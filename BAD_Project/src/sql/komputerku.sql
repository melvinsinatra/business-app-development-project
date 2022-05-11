--
-- Database: `komputerku`
--

-- --------------------------------------------------------

CREATE TABLE `member` (
  `memberID` char(5) DEFAULT NULL,
  `memberName` varchar(55) DEFAULT NULL,
  `memberGender` char(1) DEFAULT NULL,
  `memberPhone` varchar(13) DEFAULT NULL
);

INSERT INTO `member` (`memberID`, `memberName`, `memberGender`, `memberPhone`) VALUES
('ME001', 'Melvin', 'M', '081219422630'),
('ME002', 'Kevin', 'M', '081261234426'),
('ME003', 'Niki', 'F', '081219426311');


CREATE TABLE `cart` (
  `memberID` char(5) NOT NULL,
  `itemID` char(5) NOT NULL,
  `quantity` int(11) NOT NULL
);


CREATE TABLE `transactiondetail` (
  `transactionID` char(5) NOT NULL,
  `itemID` char(5) NOT NULL,
  `quantity` int(11) DEFAULT NULL
);

INSERT INTO `transactiondetail` (`transactionID`, `itemID`, `quantity`) VALUES
('TR001', 'IT001', 1),
('TR002', 'IT002', 3),
('TR002', 'IT003', 2);


CREATE TABLE `transactionheader` (
  `transactionID` char(5) NOT NULL,
  `memberID` char(5) DEFAULT NULL,
  `transactionDate` date DEFAULT NULL
);

INSERT INTO `transactionheader` (`transactionID`, `memberID`, `transactionDate`) VALUES
('TR001', 'ME002', '2022-02-12'),
('TR002', 'ME002', '2022-02-12');


CREATE TABLE `item` (
  `itemID` char(5) DEFAULT NULL,
  `itemName` varchar(55) DEFAULT NULL,
  `itemStock` int(10) DEFAULT NULL,
  `itemPrice` int(10) DEFAULT NULL
);

INSERT INTO `item` (`itemID`, `itemName`, `itemStock`, `itemPrice`) VALUES
('IT001', 'V-COLOR DDR4 PRISM PRO RGB 2x8GB 4133Mhz Memory (16GB) - Silver', 10, 50000),
('IT002', 'Processor AMD RYZEN 9 5900X Zen 3 Vermeer 12 Core 24 Threads', 5, 20000),
('IT003', 'VGA MSI Geforce RTX 3080 Ti Gaming X Trio 12GB - 12 GB DDR6X', 6, 250000);


ALTER TABLE `member`
  ADD PRIMARY KEY (`memberID`);

ALTER TABLE `cart`
  ADD PRIMARY KEY (`memberID`, `itemID`);

ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (transactionID);

ALTER TABLE `item`
  ADD PRIMARY KEY (`itemID`);

ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`transactionID`, `itemID`);


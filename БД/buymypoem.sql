-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Мар 07 2020 г., 22:51
-- Версия сервера: 8.0.19
-- Версия PHP: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `buymypoem`
--

-- --------------------------------------------------------

--
-- Структура таблицы `author`
--

CREATE TABLE `author` (
  `authorID` int NOT NULL,
  `finisedcompositions` int NOT NULL DEFAULT '0',
  `rating` float NOT NULL DEFAULT '0',
  `userID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `author`
--

INSERT INTO `author` (`authorID`, `finisedcompositions`, `rating`, `userID`) VALUES
(1, 0, 0, 1),
(2, 0, 0, 3),
(3, 0, 0, 2),
(6, 0, 0, 20);

-- --------------------------------------------------------

--
-- Структура таблицы `authorrequest`
--

CREATE TABLE `authorrequest` (
  `authorrequest` int NOT NULL,
  `authorID` int NOT NULL,
  `requestID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `comment`
--

CREATE TABLE `comment` (
  `commentID` int NOT NULL,
  `text` text NOT NULL,
  `sendingdate` date NOT NULL,
  `userID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `commentcomposition`
--

CREATE TABLE `commentcomposition` (
  `commentcompositionID` int NOT NULL,
  `commentID` int NOT NULL,
  `compositionID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `commentordering`
--

CREATE TABLE `commentordering` (
  `commentorderingID` int NOT NULL,
  `commentID` int NOT NULL,
  `orderingID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `commentrequest`
--

CREATE TABLE `commentrequest` (
  `commentrequestID` int NOT NULL,
  `commentID` int NOT NULL,
  `requestID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `composition`
--

CREATE TABLE `composition` (
  `compositionID` int NOT NULL,
  `title` varchar(70) NOT NULL,
  `description` text,
  `likes` int NOT NULL,
  `dislikes` int NOT NULL,
  `text` text NOT NULL,
  `authorID` int NOT NULL,
  `genreID` int NOT NULL,
  `typeID` int NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `composition`
--

INSERT INTO `composition` (`compositionID`, `title`, `description`, `likes`, `dislikes`, `text`, `authorID`, `genreID`, `typeID`, `status`) VALUES
(1, 'Бельгия', 'Стих про величие Бельгии', 0, 0, 'Побеждена, но не рабыня,\r\nСтоишь ты гордо без доспех,\r\nОсквернена твоя святыня,\r\nЗато душа чиста, как снег.\r\nКровавый пир в дыму пожара\r\nУстроил грозный сатана,\r\nИ под мечом его удара\r\nРазбита храбрая страна.\r\nНо дух свободный, дух могучий\r\nВеликих сил не угасил,\r\nОн, как орел, парит за тучей\r\nНад цепью доблестных могил.\r\nИ жребий правды совершится:\r\nПадет твой враг к твоим ногам\r\nИ будет с горестью молиться\r\nТвоим разбитым алтарям.', 2, 1, 2, 'В черновике'),
(2, 'В гостях', 'Про мышку', 2, 0, 'Мышь меня на чашку чая\r\nПригласила в новый дом.\r\nДолго в дом не мог войти я,\r\nВсе же влез в него с трудом.\r\nА теперь вы мне скажите:\r\nПочему и отчего\r\nНет ни дома и ни чая,\r\nНет буквально ничего!', 3, 1, 1, 'Опубликована'),
(8, 'Faith and me', 'piece of shit', 0, 0, 'Do u see what i mean?\r\nall my mindthings are free!\r\nthoughts have never been clean,\r\ni forgot to foresee\r\n\r\nhow ive lost all my friends,\r\nnevermind who they ve been,\r\nbut my pride it still stands\r\nbetween 8 and 15.', 1, 1, 1, 'В черновике'),
(13, ' Муха', 'да да я', 0, 0, 'Села муха на варенье!', 3, 2, 1, 'В черновике'),
(16, ' Береза', 'пушкин бох есенин сдох', 0, 0, 'Белая береза под моим окном...', 1, 1, 1, 'В черновике');

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE `customer` (
  `customerID` int NOT NULL,
  `paidcompositionnumber` int NOT NULL,
  `userID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `genre`
--

CREATE TABLE `genre` (
  `genreID` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `genre`
--

INSERT INTO `genre` (`genreID`, `title`, `description`) VALUES
(1, 'Стихотворение', 'небольшое литературное произведение, написанное по законам стихосложения, жанр поэтической речи.'),
(2, 'Рассказ', 'небольшое по объёму произведение, содержащее малое количество действующих лиц, а также, чаще всего, имеющее одну сюжетную линию.');

-- --------------------------------------------------------

--
-- Структура таблицы `ordering`
--

CREATE TABLE `ordering` (
  `orderingID` int NOT NULL,
  `startdate` date NOT NULL,
  `deadline` date NOT NULL,
  `cost` float NOT NULL,
  `description` text NOT NULL,
  `compositionID` int DEFAULT NULL,
  `customerID` int NOT NULL,
  `authorID` int NOT NULL,
  `typeID` int NOT NULL,
  `genreID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `paymentresourse`
--

CREATE TABLE `paymentresourse` (
  `paymentresourceID` int NOT NULL,
  `cardnumber` varchar(20) NOT NULL,
  `phonenumber` varchar(12) NOT NULL,
  `userID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `request`
--

CREATE TABLE `request` (
  `requestID` int NOT NULL,
  `description` text NOT NULL,
  `publicationdate` date NOT NULL,
  `deadline` date NOT NULL,
  `cost` float NOT NULL,
  `genreID` int NOT NULL,
  `typeID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `role`
--

CREATE TABLE `role` (
  `roleID` int NOT NULL,
  `rolename` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `tag`
--

CREATE TABLE `tag` (
  `tagID` int NOT NULL,
  `text` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `tagcomposition`
--

CREATE TABLE `tagcomposition` (
  `tagcompositionID` int NOT NULL,
  `compositionID` int NOT NULL,
  `tagID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `type`
--

CREATE TABLE `type` (
  `typeID` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `type`
--

INSERT INTO `type` (`typeID`, `title`, `description`) VALUES
(1, 'Комедия', 'художественное произведение, характеризующийся юмористическим или сатирическим подходами, и также вид драмы, в котором специфически разрешается момент действенного конфликта или борьбы.'),
(2, 'Трагедия', 'драматическое произведение, в основе которого лежит непримиримый жизненный конфликт, острое столкновение характеров и страстей, оканчивающееся чаще всего гибелью героя.');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `userID` int NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `about` text,
  `registerdate` date NOT NULL,
  `role` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`userID`, `login`, `password`, `email`, `birthdate`, `about`, `registerdate`, `role`) VALUES
(1, 'pushkin322', '123', 'pushkin@ya.ru', '1990-02-01', 'Простой мужик', '2020-02-22', 'Author'),
(2, 'alex_top', '12345', 'alex@ya.ru', '1992-03-09', 'Родился в г. Вологда', '2020-02-22', 'Author'),
(3, 'vikared', '11111', 'vika@ya.ru', '1988-06-21', 'Замужем. Двое детей', '2020-02-22', 'Author'),
(20, 'Lerrra', '-1041601825', 'lera@', '2020-03-07', NULL, '1999-11-25', 'Author'),
(21, '123', '48690', 'colya.juravlyov2011@ya.ru', '2020-03-07', NULL, '2007-11-07', 'Customer');

-- --------------------------------------------------------

--
-- Структура таблицы `userrole`
--

CREATE TABLE `userrole` (
  `userroleID` int NOT NULL,
  `userID` int NOT NULL,
  `roleID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`authorID`),
  ADD KEY `userID` (`userID`);

--
-- Индексы таблицы `authorrequest`
--
ALTER TABLE `authorrequest`
  ADD PRIMARY KEY (`authorrequest`),
  ADD KEY `authorID` (`authorID`),
  ADD KEY `requestID` (`requestID`);

--
-- Индексы таблицы `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`commentID`),
  ADD KEY `userID` (`userID`);

--
-- Индексы таблицы `commentcomposition`
--
ALTER TABLE `commentcomposition`
  ADD PRIMARY KEY (`commentcompositionID`),
  ADD KEY `commentID` (`commentID`),
  ADD KEY `compositionID` (`compositionID`);

--
-- Индексы таблицы `commentordering`
--
ALTER TABLE `commentordering`
  ADD PRIMARY KEY (`commentorderingID`),
  ADD KEY `commentID` (`commentID`),
  ADD KEY `orderingID` (`orderingID`);

--
-- Индексы таблицы `commentrequest`
--
ALTER TABLE `commentrequest`
  ADD PRIMARY KEY (`commentrequestID`),
  ADD KEY `commentID` (`commentID`),
  ADD KEY `requestID` (`requestID`);

--
-- Индексы таблицы `composition`
--
ALTER TABLE `composition`
  ADD PRIMARY KEY (`compositionID`),
  ADD KEY `authorID` (`authorID`),
  ADD KEY `genreID` (`genreID`),
  ADD KEY `typeID` (`typeID`);

--
-- Индексы таблицы `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerID`),
  ADD KEY `userID` (`userID`);

--
-- Индексы таблицы `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genreID`);

--
-- Индексы таблицы `ordering`
--
ALTER TABLE `ordering`
  ADD PRIMARY KEY (`orderingID`),
  ADD KEY `compositionID` (`compositionID`),
  ADD KEY `customerID` (`customerID`),
  ADD KEY `authorID` (`authorID`),
  ADD KEY `typeID` (`typeID`),
  ADD KEY `genreID` (`genreID`);

--
-- Индексы таблицы `paymentresourse`
--
ALTER TABLE `paymentresourse`
  ADD PRIMARY KEY (`paymentresourceID`),
  ADD KEY `userID` (`userID`);

--
-- Индексы таблицы `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`requestID`),
  ADD KEY `genreID` (`genreID`),
  ADD KEY `typeID` (`typeID`);

--
-- Индексы таблицы `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`roleID`);

--
-- Индексы таблицы `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`tagID`);

--
-- Индексы таблицы `tagcomposition`
--
ALTER TABLE `tagcomposition`
  ADD PRIMARY KEY (`tagcompositionID`),
  ADD KEY `compositionID` (`compositionID`),
  ADD KEY `tagID` (`tagID`);

--
-- Индексы таблицы `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`typeID`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- Индексы таблицы `userrole`
--
ALTER TABLE `userrole`
  ADD PRIMARY KEY (`userroleID`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `author`
--
ALTER TABLE `author`
  MODIFY `authorID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `authorrequest`
--
ALTER TABLE `authorrequest`
  MODIFY `authorrequest` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `comment`
--
ALTER TABLE `comment`
  MODIFY `commentID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `commentcomposition`
--
ALTER TABLE `commentcomposition`
  MODIFY `commentcompositionID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `commentordering`
--
ALTER TABLE `commentordering`
  MODIFY `commentorderingID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `commentrequest`
--
ALTER TABLE `commentrequest`
  MODIFY `commentrequestID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `composition`
--
ALTER TABLE `composition`
  MODIFY `compositionID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT для таблицы `customer`
--
ALTER TABLE `customer`
  MODIFY `customerID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `genre`
--
ALTER TABLE `genre`
  MODIFY `genreID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `ordering`
--
ALTER TABLE `ordering`
  MODIFY `orderingID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `paymentresourse`
--
ALTER TABLE `paymentresourse`
  MODIFY `paymentresourceID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `request`
--
ALTER TABLE `request`
  MODIFY `requestID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `role`
--
ALTER TABLE `role`
  MODIFY `roleID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `tag`
--
ALTER TABLE `tag`
  MODIFY `tagID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `tagcomposition`
--
ALTER TABLE `tagcomposition`
  MODIFY `tagcompositionID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `type`
--
ALTER TABLE `type`
  MODIFY `typeID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `userID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT для таблицы `userrole`
--
ALTER TABLE `userrole`
  MODIFY `userroleID` int NOT NULL AUTO_INCREMENT;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `author`
--
ALTER TABLE `author`
  ADD CONSTRAINT `author_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Ограничения внешнего ключа таблицы `authorrequest`
--
ALTER TABLE `authorrequest`
  ADD CONSTRAINT `authorrequest_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `author` (`authorID`),
  ADD CONSTRAINT `authorrequest_ibfk_2` FOREIGN KEY (`requestID`) REFERENCES `request` (`requestID`);

--
-- Ограничения внешнего ключа таблицы `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Ограничения внешнего ключа таблицы `commentcomposition`
--
ALTER TABLE `commentcomposition`
  ADD CONSTRAINT `commentcomposition_ibfk_1` FOREIGN KEY (`commentID`) REFERENCES `comment` (`commentID`),
  ADD CONSTRAINT `commentcomposition_ibfk_2` FOREIGN KEY (`compositionID`) REFERENCES `composition` (`compositionID`);

--
-- Ограничения внешнего ключа таблицы `commentordering`
--
ALTER TABLE `commentordering`
  ADD CONSTRAINT `commentordering_ibfk_1` FOREIGN KEY (`commentID`) REFERENCES `comment` (`commentID`),
  ADD CONSTRAINT `commentordering_ibfk_2` FOREIGN KEY (`orderingID`) REFERENCES `ordering` (`orderingID`);

--
-- Ограничения внешнего ключа таблицы `commentrequest`
--
ALTER TABLE `commentrequest`
  ADD CONSTRAINT `commentrequest_ibfk_1` FOREIGN KEY (`commentID`) REFERENCES `comment` (`commentID`),
  ADD CONSTRAINT `commentrequest_ibfk_2` FOREIGN KEY (`requestID`) REFERENCES `request` (`requestID`);

--
-- Ограничения внешнего ключа таблицы `composition`
--
ALTER TABLE `composition`
  ADD CONSTRAINT `composition_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `author` (`authorID`),
  ADD CONSTRAINT `genreID` FOREIGN KEY (`genreID`) REFERENCES `genre` (`genreID`),
  ADD CONSTRAINT `typeID` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`);

--
-- Ограничения внешнего ключа таблицы `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Ограничения внешнего ключа таблицы `ordering`
--
ALTER TABLE `ordering`
  ADD CONSTRAINT `ordering_ibfk_1` FOREIGN KEY (`compositionID`) REFERENCES `composition` (`compositionID`),
  ADD CONSTRAINT `ordering_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`),
  ADD CONSTRAINT `ordering_ibfk_3` FOREIGN KEY (`authorID`) REFERENCES `author` (`authorID`),
  ADD CONSTRAINT `ordering_ibfk_4` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`),
  ADD CONSTRAINT `ordering_ibfk_5` FOREIGN KEY (`genreID`) REFERENCES `genre` (`genreID`);

--
-- Ограничения внешнего ключа таблицы `paymentresourse`
--
ALTER TABLE `paymentresourse`
  ADD CONSTRAINT `paymentresourse_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Ограничения внешнего ключа таблицы `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`genreID`) REFERENCES `genre` (`genreID`),
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`);

--
-- Ограничения внешнего ключа таблицы `tagcomposition`
--
ALTER TABLE `tagcomposition`
  ADD CONSTRAINT `tagcomposition_ibfk_1` FOREIGN KEY (`compositionID`) REFERENCES `composition` (`compositionID`),
  ADD CONSTRAINT `tagcomposition_ibfk_2` FOREIGN KEY (`tagID`) REFERENCES `tag` (`tagID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

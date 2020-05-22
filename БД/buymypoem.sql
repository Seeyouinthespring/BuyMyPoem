-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Май 22 2020 г., 19:41
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
  `userID` int NOT NULL,
  `cardNumber` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `author`
--

INSERT INTO `author` (`authorID`, `finisedcompositions`, `rating`, `userID`, `cardNumber`) VALUES
(16, 1, 0, 40, NULL);

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
  `authorID` int DEFAULT NULL,
  `genreID` int NOT NULL,
  `typeID` int NOT NULL,
  `status` varchar(20) NOT NULL,
  `ownerID` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `composition`
--

INSERT INTO `composition` (`compositionID`, `title`, `description`, `likes`, `dislikes`, `text`, `authorID`, `genreID`, `typeID`, `status`, `ownerID`) VALUES
(36, 'Сочи', 'Стих про красоту города', 0, 0, '6 утра и запах тянет любоваться волей волн, \r\nНо в поток свежего бриза я ни капли не влюблен,\r\nКак бы ни было прекрасно солнце, что взлетает с гор,\r\nОпуская свои блики на еловый частокол.\r\n\r\nПустота курортных улиц дарит чувство всех свобод,\r\nА массивы из отелей современный натюрморт.\r\nС пляжа белизна вершин успокаивает глаз,\r\nНо ведь далеко не в топе для меня этот контраст.', 16, 3, 2, 'Опубликовано', NULL),
(37, 'Школа', 'для заказа', 0, 0, '<Что-то смешное про школу>', NULL, 2, 3, 'Преобретена', 9),
(38, 'Hoplessness', 'so so', 0, 0, 'do u see what i mean?\r\nall my mindthings are free!\r\nthoughts have never been clean,\r\ni forgot to foresee\r\n\r\nhow ive lost all my friends,\r\nnevermind who they\'ve been,\r\nbut my pride it still stands\r\nbetween 8 and 15.', 16, 3, 2, 'Опубликовано', NULL),
(39, 'Свобода', 'Либерализм тут ни при чем!', 0, 0, 'Закинул за сеть, \r\nЧтоб снова засесть\r\nВ мерцающий прямоугольник. \r\n\r\nУсловности прочь! \r\nИз вечера в ночь\r\nСредой заменяется вторник. \r\n\r\nО нужном забыв, \r\nВлетаю в порыв\r\nАналоговых сигналов. \r\n\r\nДраконы в ушах, \r\nПью чай неспеша, \r\nСвободнее всех либералов... \r\n\r\nЖизнь очень проста\r\nОт лап до хвоста, \r\nКогда ты рождён был собакой. \r\n\r\nНет обязательств, \r\nДолгов и ругательств, \r\nБудь лапочкой иль забиякой. \r\n\r\nС людьми все не так, \r\nЗдесь каждый простак - \r\nОдин из семи миллиардов\r\n\r\nСистеме под стать, \r\nНо хочется стать\r\nСлоненком или гепардом. \r\n\r\n\"Так дай же мне сил\"-\r\nУ бога просил, \r\nПотом вспоминал, что не верю. \r\n\r\nТы сам посмотри, \r\nМне тошно внутри, \r\nКак в клетке дикому зверю. \r\n\r\nВ итоге я - раб, \r\nХотя не был слаб. \r\nЗвучит очень парадоксально! \r\n\r\nЗависим от всех, \r\nПроцент на успех\r\nВсё ближе к совсем минимальным. \r\n\r\nПринять не хочу, \r\nНо не по плечу\r\nБороться, а значит иначе:\r\n\r\nБежать в темноту, \r\nЧто тает во рту\r\nЗагадочным вкусом удачи. \r\n\r\nИ вот как луна\r\nОстаётся одна, \r\nИду без машин и порталов\r\n\r\nИз общества прочь\r\nВ манящюю ночь, \r\nСвободнее всех либералов...', 16, 3, 2, 'Опубликовано', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE `customer` (
  `customerID` int NOT NULL,
  `paidcompositionnumber` int NOT NULL DEFAULT '0',
  `userID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `customer`
--

INSERT INTO `customer` (`customerID`, `paidcompositionnumber`, `userID`) VALUES
(9, 1, 41),
(10, 0, 42);

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
(1, 'Выберите жанр произведения', 'Выберите жанр произведения'),
(2, 'Рассказ', 'небольшое по объёму произведение, содержащее малое количество действующих лиц, а также, чаще всего, имеющее одну сюжетную линию.'),
(3, 'Стихотворение', 'небольшое литературное произведение, написанное по законам стихосложения, жанр поэтической речи.'),
(5, 'Басня', 'жанр дидактической литературы: короткий рассказ в стихах или прозе с прямо сформулированным моральным выводом, придающим рассказу аллегорический смысл.'),
(6, 'Сценарий', 'итературно-драматическое произведение, написанное как основа для постановки кино- или телефильма, и других мероприятий в театре и иных местах.'),
(7, 'Роман', 'художественное произведение большого объема, в котором развернуто повествуется о событиях в жизни главных действующих лиц и их судьбах.');

-- --------------------------------------------------------

--
-- Структура таблицы `mark`
--

CREATE TABLE `mark` (
  `markID` int NOT NULL,
  `compositionID` int NOT NULL,
  `userID` int NOT NULL,
  `mark` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `genreID` int NOT NULL,
  `status` varchar(20) DEFAULT 'processing'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `request`
--

CREATE TABLE `request` (
  `requestID` int NOT NULL,
  `description` text NOT NULL,
  `customerID` int NOT NULL,
  `publicationdate` date NOT NULL,
  `deadline` date NOT NULL,
  `cost` float NOT NULL,
  `genreID` int NOT NULL,
  `typeID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `request`
--

INSERT INTO `request` (`requestID`, `description`, `customerID`, `publicationdate`, `deadline`, `cost`, `genreID`, `typeID`) VALUES
(1142, 'Что-нибудь грустное про школу', 9, '2020-05-22', '2020-05-28', 500, 3, 2),
(1143, 'Что-нибудь простенькое про любовь', 10, '2020-05-22', '2020-05-28', 100, 2, 2),
(1144, 'Длинная социальная сатира на обстановку в стране', 10, '2020-05-22', '2020-07-18', 1045, 3, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `support`
--

CREATE TABLE `support` (
  `id` int NOT NULL,
  `userID` int NOT NULL,
  `msg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `support`
--

INSERT INTO `support` (`id`, `userID`, `msg`) VALUES
(7, 40, 'Мне кажется пользователь с логином Bobby пытается меня обмануть');

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
(1, 'Выберите тип произведения', 'Выберите тип произведения'),
(2, 'Трагедия', 'драматическое произведение, в основе которого лежит непримиримый жизненный конфликт, острое столкновение характеров и страстей, оканчивающееся чаще всего гибелью героя.'),
(3, 'Комедия', 'художественное произведение, характеризующийся юмористическим или сатирическим подходами, и также вид драмы, в котором специфически разрешается момент действенного конфликта или борьбы.'),
(5, 'Драма', 'род литературного произведения, в котором событие не рассказывается автором, а всецело представляется действующими лицами в живой обыкновенной речи; предназначается преимущественно для театра'),
(6, 'Триллер', 'жанр произведений литературы и кино, нацеленный вызвать у зрителя или читателя чувства тревожного ожидания, волнения или страха.'),
(7, 'Путешествие', 'литературный жанр, в основе которого описание странствий героя'),
(8, 'Ужасы', 'жанр фантастики, который предназначен устрашить, напугать, шокировать или вызвать отвращение у своих читателей или зрителей, вызвав у них чувства ужаса и шока'),
(9, 'Детектив', 'преимущественно литературный и кинематографический жанр, произведения которого описывают процесс исследования загадочного происшествия с целью выяснения его обстоятельств и раскрытия загадки');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `userID` int NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `about` text,
  `registerdate` date NOT NULL,
  `role` varchar(15) DEFAULT NULL,
  `photo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'D:/repository/default.jpg'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`userID`, `login`, `password`, `email`, `birthdate`, `about`, `registerdate`, `role`, `photo`) VALUES
(40, 'seeyouinthespring', '$2a$10$xUl4qZD6lShvZtInLxTYJ.Q9pCOiAMhQu7CyA8BP7xTPatBeKlzoi', 'colya.juravlyov@ya.ru', '1999-03-27', NULL, '2020-05-22', 'Author', 'D:/repository/default.jpg'),
(41, 'Bobby', '$2a$10$3/XkP1o5I2VAe980jCrafOlZdeLSuMpGrq0EOb1DS8RQu.6id9ltq', 'bobbytheman@ya.ru', '2005-02-04', NULL, '2020-05-22', 'Customer', 'D:/repository/default.jpg'),
(42, 'ksin', '$2a$10$FWdEFxjcdpFuub2cot9m9eOt907SdRhgHZNPb6kkZFPwq7CtvqRVO', 'seenseven@mail.ru', '1993-06-16', NULL, '2020-05-22', 'Customer', 'D:/repository/default.jpg'),
(43, 'admin', '$2a$10$3/XkP1o5I2VAe980jCrafOlZdeLSuMpGrq0EOb1DS8RQu.6id9ltq', 'admin47563@ya.ru', '2019-06-07', 'a', '2020-05-22', 'Service', 'D:/repository/default.jpg');

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
  ADD KEY `typeID` (`typeID`),
  ADD KEY `ownerID` (`ownerID`);

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
-- Индексы таблицы `mark`
--
ALTER TABLE `mark`
  ADD PRIMARY KEY (`markID`),
  ADD KEY `compositionID` (`compositionID`,`userID`),
  ADD KEY `userID` (`userID`);

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
-- Индексы таблицы `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`requestID`),
  ADD KEY `genreID` (`genreID`),
  ADD KEY `typeID` (`typeID`);

--
-- Индексы таблицы `support`
--
ALTER TABLE `support`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userID` (`userID`);

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
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `author`
--
ALTER TABLE `author`
  MODIFY `authorID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT для таблицы `authorrequest`
--
ALTER TABLE `authorrequest`
  MODIFY `authorrequest` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT для таблицы `comment`
--
ALTER TABLE `comment`
  MODIFY `commentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2428;

--
-- AUTO_INCREMENT для таблицы `commentcomposition`
--
ALTER TABLE `commentcomposition`
  MODIFY `commentcompositionID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `commentordering`
--
ALTER TABLE `commentordering`
  MODIFY `commentorderingID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT для таблицы `commentrequest`
--
ALTER TABLE `commentrequest`
  MODIFY `commentrequestID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2397;

--
-- AUTO_INCREMENT для таблицы `composition`
--
ALTER TABLE `composition`
  MODIFY `compositionID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT для таблицы `customer`
--
ALTER TABLE `customer`
  MODIFY `customerID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `genre`
--
ALTER TABLE `genre`
  MODIFY `genreID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `mark`
--
ALTER TABLE `mark`
  MODIFY `markID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT для таблицы `ordering`
--
ALTER TABLE `ordering`
  MODIFY `orderingID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `request`
--
ALTER TABLE `request`
  MODIFY `requestID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1148;

--
-- AUTO_INCREMENT для таблицы `support`
--
ALTER TABLE `support`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `type`
--
ALTER TABLE `type`
  MODIFY `typeID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `userID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

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
  ADD CONSTRAINT `ownerID` FOREIGN KEY (`ownerID`) REFERENCES `customer` (`customerID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `typeID` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`);

--
-- Ограничения внешнего ключа таблицы `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Ограничения внешнего ключа таблицы `mark`
--
ALTER TABLE `mark`
  ADD CONSTRAINT `compositionID` FOREIGN KEY (`compositionID`) REFERENCES `composition` (`compositionID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

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
-- Ограничения внешнего ключа таблицы `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`genreID`) REFERENCES `genre` (`genreID`),
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

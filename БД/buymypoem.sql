-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Май 15 2020 г., 16:59
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
(1, 0, 0, 1, ''),
(2, 0, 0, 3, ''),
(3, 0, 0, 2, ''),
(7, 0, 0, 23, '1111111111111111'),
(8, 0, 0, 25, ''),
(15, 0, 0, 37, NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `authorrequest`
--

CREATE TABLE `authorrequest` (
  `authorrequest` int NOT NULL,
  `authorID` int NOT NULL,
  `requestID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `authorrequest`
--

INSERT INTO `authorrequest` (`authorrequest`, `authorID`, `requestID`) VALUES
(9, 8, 1),
(12, 8, 2);

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

--
-- Дамп данных таблицы `comment`
--

INSERT INTO `comment` (`commentID`, `text`, `sendingdate`, `userID`) VALUES
(1, 'Остроумно', '2020-04-21', 23),
(11, 'Все не так', '2020-05-08', 27),
(12, 'Откликнитесь уродыыы', '2020-05-11', 27),
(13, 'эгегей', '2020-05-11', 27),
(14, 'Ща исправлю', '2020-05-13', 25),
(15, 'Дерьмо, переделывай!', '2020-05-13', 27);

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

--
-- Дамп данных таблицы `commentordering`
--

INSERT INTO `commentordering` (`commentorderingID`, `commentID`, `orderingID`) VALUES
(8, 11, 4),
(9, 14, 4),
(10, 15, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `commentrequest`
--

CREATE TABLE `commentrequest` (
  `commentrequestID` int NOT NULL,
  `commentID` int NOT NULL,
  `requestID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `commentrequest`
--

INSERT INTO `commentrequest` (`commentrequestID`, `commentID`, `requestID`) VALUES
(2, 12, 2),
(3, 13, 2);

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
(1, 'Бельгия', 'Стих про величие Бельгии', 0, 0, 'Побеждена, но не рабыня,\r\nСтоишь ты гордо без доспех,\r\nОсквернена твоя святыня,\r\nЗато душа чиста, как снег.\r\nКровавый пир в дыму пожара\r\nУстроил грозный сатана,\r\nИ под мечом его удара\r\nРазбита храбрая страна.\r\nНо дух свободный, дух могучий\r\nВеликих сил не угасил,\r\nОн, как орел, парит за тучей\r\nНад цепью доблестных могил.\r\nИ жребий правды совершится:\r\nПадет твой враг к твоим ногам\r\nИ будет с горестью молиться\r\nТвоим разбитым алтарям.', 7, 3, 2, 'В черновике', NULL),
(2, 'В гостях', 'Про мышку', 2, 0, 'Мышь меня на чашку чая\r\nПригласила в новый дом.\r\nДолго в дом не мог войти я,\r\nВсе же влез в него с трудом.\r\nА теперь вы мне скажите:\r\nПочему и отчего\r\nНет ни дома и ни чая,\r\nНет буквально ничего!', 7, 3, 3, 'Опубликовано', NULL),
(8, 'Faith and me', 'piece of shit', 0, 3, 'Do u see what i mean?\r\nall my mindthings are free!\r\nthoughts have never been clean,\r\ni forgot to foresee\r\n\r\nhow ive lost all my friends,\r\nnevermind who they ve been,\r\nbut my pride it still stands\r\nbetween 8 and 15.', 1, 3, 3, 'Опубликовано', NULL),
(13, ' Муха', 'да да я', 0, 0, 'Села муха на варенье!', 7, 2, 3, 'Опубликовано', NULL),
(16, ' Береза', 'пушкин бох есенин сдох', 0, 0, 'Белая береза под моим окном...', 7, 3, 3, 'В черновике', NULL),
(17, 'На изгибы мёртвых', 'Просто стих', 4354, 34545, 'На изгибы мёртвых улиц ляжет томный нежный взгляд.\r\nСтан серьёзный, шаг неспешный, 40 месяцев подряд.\r\nДень за днём и год за годом, в перемены веры нет.\r\nКрыши, ветки и заборы, под стабильный серый свет.\r\n\r\nЗнает всех людей района, кто противней, кто быстрей.\r\nСидя, с крыши пар пускает из потрёпанных ноздрей.\r\nГрациозные движенья: шаг, трусца, бегом, прыжок.\r\nИ уже в ларьке с едою. К повару. За ним должок.\r\n\r\nПодкрепившись тем, что дали. Неспеша по всем делам:\r\nПоздороваться с подругой, птиц подергать тут и там,\r\nПрочесать дорожки парка на потеху малышей,\r\nС высоты поникшей ивы потаращить на лещей.\r\n\r\nПод ногами километры за спиною только хвост.\r\nНастроенье переменно, то на крышу, то под мост.\r\nЯзыка шершавость бесит. Шерсть бы не стереть до дыр.\r\nВот у мамы был приятней. Ключевое слово был...\r\n\r\nНо не время капать слезы. Собираются коты\r\nВновь побиться до психоза за принцессу нищеты.\r\nЖаль сегодня без мужчинок, жизнь пустеет с каждым днём.\r\nГордо, не подав и виду уплывет играть с огнём.\r\n\r\nУрны жечь - не брать бумажки. Здесь не знают слова враг.\r\nНет желаний силы мерить, что касается и благ.\r\nСистематика не ставит рамок для понятия бомж.\r\nНет деления на бедных и господ или вельмож.\r\n\r\nОтражаясь в жёлтых глазках, догорит обычный день.\r\nС цветом стен почти сольётся в миг ушастенькая тень.\r\nХоть во мраке страх не встанет, взгляд кошачий режет тьму.\r\nНо усталость не позволит удивляться ничему.\r\n\r\nЖизнь проста но неизменна, завтра снова как-то так:\r\nКрыши, люди, перекрёстки, мост, огни, закат, чердак.\r\nСон придёт сквозь лай далёкий, одинокий, без угроз.\r\nВедь судьба одна и та же — не для строк стихов и проз.', 7, 3, 3, 'Опубликовано', NULL),
(18, 'Дюжина пар назойливых глаз', 'Держу в курсе', 232, 1232, 'Дюжина пар назойливых глаз, простые движенья, пустые стаканы.\r\nОни все позабудут про кто, что сказал, расставляя друг другу под ноги капканы.\r\nЧто же я тут забыл? Бит взорвётся сильней и оставит вопрос без ответа.\r\nЗапах пьяных волос, голос детской мечты в середине двадцатого лета.\r\n\r\nВроде даже привык, как снижает свой темп беспорядочный пляс силуэтов,\r\nКак вселяет цикличность в статический фон россыпь красно-безумных портретов.\r\nВзгляд мой точку одну изучает давно сантиметром ни выше, ни ниже.\r\nЯ смотрю по прямой, но все то, что вокруг, наизусть знаю, хоть и не вижу.\r\n\r\nПусты мысли, идеи, слова, споры, звуки в молчании разум сильнее.\r\nСреди кучи чужих, бестолковых людей одиночество лупит больнее.\r\nПередоз или сон? Адекватен, влюблён? Я не тот за кого меня держат!\r\nЯ для них и не бог, но и точно не раб, наблюдая за всем где-то между.\r\n\r\nОн же мог не блевать где-то там в уголке, а узнать что то важное вместо.\r\nА она могла мирно в кровати сопеть, а не грудью трясти возле шеста.\r\nХотя что я ворчу? Мне не все ли равно? Убирать за ним точно не буду.\r\nА на годную грудь почему б не взглянуть, рот разинув, роняя посуду.\r\n\r\n\"Эй, братан! Ты чего приуныл? Хватит тухнуть! Возьми ка бутылочку пива!\"\r\nНу и нахер ты дал мне вот это дерьмо? Не дождавшись ответа ушёл некрасиво?\r\nХоть на пару минут надо выйти во двор, отпустить колдовство психодела.\r\nСвежий ветер ночной, Россыпь звёзд в небесах, вобщем все не для пьяного тела\r\n\r\nЧто ж, наверное, мне никогда не понять, почему им все это так сильно по нраву.\r\nВеселиться, курить, друг на друге скакать, запивая отравой отраву.\r\nИм же легче проблемы и мысли свои утопить и забыть в алкоголе.\r\nСоздать собственный мир, где не надо вникать, существуя как будто в приколе.\r\n\r\nНу а я что? Я тоже хотел. Но так просто свой мозг отключить не по силам.\r\nЭто сложно порой — слушать шум в голове и пульсацию крови по жилам\r\nЯ здесь самый чужой, мне не нужно здесь быть, будто взрослому в школьном буфете.\r\nСкоро выдвинусь в собственный мир а пока... А пока на меня дует ветер.\r\n\r\nСнова в мыслях повеет пейзажем до боли знакомым но очень далёким.\r\nЯ вовсе не знал, кем я был все то время, пока я не был одиноким.\r\nВ мечтах растворяясь, забыв про реальность я падал и падал и падал.\r\nИ понял, что где-то свернул не туда, лишь добравшись, куда мне не надо.', 7, 3, 2, 'Опубликовано', NULL),
(21, 'Новое произведение', 'просто новое произведение', 0, 0, 'дада, новое', 7, 3, 3, 'В черновике', NULL),
(31, 'Заказ1', 'Для тестового заказа', 0, 0, 'Отражаясь в жёлтых глазках, догорит обычный день.\r\nС цветом стен почти сольётся в миг ушастенькая тень.\r\nХоть во мраке страх не встанет, взгляд кошачий режет тьму.\r\nНо усталость не позволит удивляться ничему.\r\n\r\nЖизнь проста но неизменна, завтра снова как-то так:\r\nКрыши, люди, перекрёстки, мост, огни, закат, чердак.\r\nСон придёт сквозь лай далёкий, одинокий, без угроз.\r\nВедь судьба одна и та же — не для строк стихов и проз.', NULL, 3, 2, 'Преобретена', 3),
(32, 'Заказ2', 'Тестовый рассказ', 0, 0, 'Я поел пельмени', NULL, 2, 3, 'Преобретена', 3),
(34, 'Супермен', 'Рассказ про супермена', 0, 0, 'Супермен умрет в конце БПС', 8, 2, 2, 'Опубликовано', NULL),
(35, 'Test', 'English', 0, 0, 'or im crazy fool\r\nsuspicious selfish guy\r\nbut benifit will rule\r\nfor endlessness of time\r\n\r\nmotivation\'s carrying\r\nout of stratosphere\r\ni know what im searching\r\ni just don\'t know where', 8, 3, 3, 'На предпросмотре', NULL);

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
(1, 0, 21),
(2, 0, 22),
(3, 0, 27),
(7, 0, 38),
(8, 0, 39);

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
(3, 'Стихотворение', 'небольшое литературное произведение, написанное по законам стихосложения, жанр поэтической речи.');

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

--
-- Дамп данных таблицы `mark`
--

INSERT INTO `mark` (`markID`, `compositionID`, `userID`, `mark`) VALUES
(1, 1, 24, 0);

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

--
-- Дамп данных таблицы `ordering`
--

INSERT INTO `ordering` (`orderingID`, `startdate`, `deadline`, `cost`, `description`, `compositionID`, `customerID`, `authorID`, `typeID`, `genreID`, `status`) VALUES
(2, '2020-05-04', '2020-05-22', 100, 'тупа для примера', 1, 1, 8, 3, 2, 'Ready'),
(4, '2020-05-08', '2020-06-26', 1234, 'рофлостих про коронавирус', 35, 3, 8, 3, 3, 'Ready'),
(5, '2020-05-11', '2020-03-27', 88, 'лалалалалалалалал', NULL, 3, 8, 1, 1, 'processing');

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
(1, 'Стих про зиму', 2, '2020-03-23', '2020-03-30', 100, 1, 2),
(2, 'Очень смешной рассказ про Путина', 3, '2020-03-23', '2020-04-23', 10000, 2, 1),
(6, 'Не поверишь, это описание)', 1, '2020-03-24', '2020-03-24', 1000, 1, 1),
(7, 'ÑÑÐ¸Ñ Ð¾ Ð½ÐµÐ½Ð°Ð²Ð¸ÑÑÐ¸ Ðº ÑÑÑÑ', 2, '2020-04-21', '2020-04-24', 1000, 1, 1),
(8, 'бла бла бла проверяю русский\r\n', 2, '2020-04-21', '2020-04-30', 0, 1, 1),
(9, 'пам пам пам, идите все к хуям', 2, '2020-04-21', '2020-04-30', 0, 1, 1);

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
(1, 'Выберите тип произведения', 'Выберите тип произведения'),
(2, 'Трагедия', 'драматическое произведение, в основе которого лежит непримиримый жизненный конфликт, острое столкновение характеров и страстей, оканчивающееся чаще всего гибелью героя.'),
(3, 'Комедия', 'художественное произведение, характеризующийся юмористическим или сатирическим подходами, и также вид драмы, в котором специфически разрешается момент действенного конфликта или борьбы.');

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
(1, 'pushkin322', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'pushkin@ya.ru', '1990-02-01', 'Простой мужик', '2020-02-22', 'Author', 'D:/repository/default.jpg'),
(2, 'alex_top', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'alex@ya.ru', '1992-03-09', 'Родился в г. Вологда', '2020-02-22', 'Author', 'D:/repository/default.jpg'),
(3, 'vik', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'vika@ya.ru', '1988-06-21', 'Замужем. Двое детей', '2020-02-22', 'Author', 'D:/repository/default.jpg'),
(21, '123', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'colya.juravlyov2011@ya.ru', '2020-03-07', NULL, '2007-11-07', 'Customer', 'D:/repository/default.jpg'),
(22, 'arranay', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'val@gmail.com', '2020-03-14', NULL, '2020-03-14', 'Customer', 'D:/repository/arranay.jpg'),
(23, 'arranayA', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'val@gmail.com', '2020-03-15', 'Скромный писатель', '2020-03-15', 'Author', 'D:/repository/arranayA.jpg'),
(24, 'admin', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'admin', '2020-03-15', NULL, '2020-03-15', 'Service', 'D:/repository/default.jpg'),
(25, 'seeyouinthespring', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'colya.juravlyov2011@ya.ru', '2020-03-23', NULL, '1999-03-29', 'Author', 'D:/repository/seeyouinthespring.jpg'),
(27, 'see', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', 'seesee', '2020-03-23', NULL, '2020-03-02', 'Customer', 'D:/repository/default.jpg'),
(37, 'new', '$2a$10$C/DpC9VJclaJx/BhFHLOfepRpLhUXb9GaYNrUa2Y/AK8Dk/Zuy8kq', 'new', '2020-04-20', NULL, '2020-04-20', 'Author', 'D:/repository/default.jpg'),
(38, '111', '$2a$10$A4Z6Xh0ZsWQgvSYANmRr0.NAmNcei5IHtdxrKMGx8pm0gRtVLuAkO', '1', '2020-04-20', NULL, '2020-04-20', 'Customer', 'D:/repository/default.jpg'),
(39, 'arranay-------', '$2a$10$ps7TmGvJ8Puy93RkNPP6Z.7xtlxFx.K.A1QYmkg8qoGhXUe3CkX2m', '+79209180027', '2020-04-20', NULL, '2020-04-20', 'Customer', 'D:/repository/default.jpg');

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
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `author`
--
ALTER TABLE `author`
  MODIFY `authorID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `authorrequest`
--
ALTER TABLE `authorrequest`
  MODIFY `authorrequest` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT для таблицы `comment`
--
ALTER TABLE `comment`
  MODIFY `commentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `commentcomposition`
--
ALTER TABLE `commentcomposition`
  MODIFY `commentcompositionID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `commentordering`
--
ALTER TABLE `commentordering`
  MODIFY `commentorderingID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `commentrequest`
--
ALTER TABLE `commentrequest`
  MODIFY `commentrequestID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `composition`
--
ALTER TABLE `composition`
  MODIFY `compositionID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT для таблицы `customer`
--
ALTER TABLE `customer`
  MODIFY `customerID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT для таблицы `genre`
--
ALTER TABLE `genre`
  MODIFY `genreID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `mark`
--
ALTER TABLE `mark`
  MODIFY `markID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT для таблицы `ordering`
--
ALTER TABLE `ordering`
  MODIFY `orderingID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `paymentresourse`
--
ALTER TABLE `paymentresourse`
  MODIFY `paymentresourceID` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `request`
--
ALTER TABLE `request`
  MODIFY `requestID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
  MODIFY `typeID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `userID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

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

INSERT INTO public.author (id_author,name,surname) VALUES
(20,'Antoine','Exupery'),
(2,'Ernest','Hemingway'),
(3,'Kathie','Sierra'),
(4,'Karl','May'),
(5,'Mykola','Gogol'),
(6,'Taras','Shevchenko'),
(100,'Sun','Microsystem'),
(11,'Stephen','King'),
(7,'Pavol Orszagh','Hviezdoslav'),
(13,'Erich M.','Remarque'),
(14,'Erich M.','Remarque2');
INSERT INTO public.book (name,count,id_author,id_book) VALUES
('Java Programming',3,3,2),
('The Old Man And The Sea',7,2,3),
('Winnetou 1',2,4,4),
('Winnetou 2',3,4,5),
('Winnetou 3',8,4,6),
('Poklad na striebornom jazere',3,4,7),
('Java 1',10,100,8),
('Java 2',10,100,9),
('Java 3',3,100,10),
('Hajnikova zena',5,7,11);
INSERT INTO public.book (name,count,id_author,id_book) VALUES
('Cujo',5,11,13),
('Night in Lisabon',5,13,15);


INSERT INTO library (id_library,name,address,city) VALUES
(13,'Kniznica Raca','Nobelova 25','Bratislava'),
(2,'Kniznica Ruzinov','Tomasikova 50','Bratislava'),
(3,'Kniznica Dubravka','Repasskeho 1','Bratislava'),
(4,'Kniznica stare mesto','sochova 23','Bratislava'),
(5,'Kniznica ruzovce ','ruzovce 23','Bratislava'),
(6,'Kniznica Svaty jur ','prostredna 23','Svaty jur '),
(7,'Kniznica Stare Mesto','Venturska 12','Bratislava'),
(8,'Kniznica Roznava','Ciklerova 5','Roznava'),
(9,'Kniznica Svaty jur ','prostredna 23','Svaty jur '),
(10,'Kniznica nove mesto ','racianska 23','Bratislava ');

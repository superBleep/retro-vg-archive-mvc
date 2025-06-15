INSERT INTO platform (name, manufacturer, release) VALUES
('PlayStation 2', 'Sony', DATE '2000-03-04'),
('Nintendo 64', 'Nintendo', DATE '1996-06-23'),
('Game Boy Advance', 'Nintendo', DATE '2001-03-21');

INSERT INTO emulator (name, developer, release) VALUES
('PCSX2', 'PCSX2 Team', DATE '2002-03-01'),
('Project64', 'Zilmar', DATE '2001-05-26'),
('mGBA', 'endrift', DATE '2014-10-01');

INSERT INTO emulator_platform (emulator_id, platform_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO game (title, developer, publisher, genre, platform_id) VALUES
('Final Fantasy X', 'Square', 'Square Enix', 'RPG', 1),
('Super Mario 64', 'Nintendo', 'Nintendo', 'Platformer', 2),
('Metroid Fusion', 'Nintendo', 'Nintendo', 'Action', 3);

INSERT INTO game_version (id, game_id, release, notes) VALUES
('1.0.0', 1, '2001-07-19', 'Original JP release'),
('1.0.0', 2, '1996-06-23', 'Original N64 release'),
('1.0.0', 3, '2002-11-17', 'Initial GBA version');
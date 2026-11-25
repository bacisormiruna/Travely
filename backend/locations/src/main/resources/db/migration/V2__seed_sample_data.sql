INSERT INTO locations (name, country, latitude, longitude) VALUES
                                                               ('Brașov',      'România', 45.6427, 25.5887),
                                                               ('Bran',        'România', 45.5150, 25.3672),
                                                               ('Sibiu',       'România', 45.7983, 24.1256),
                                                               ('Cluj-Napoca', 'România', 46.7712, 23.6236)
    ON CONFLICT (name, country) DO NOTHING;

INSERT INTO attractions (name, description, category, location_id, latitude, longitude, entry_price)
SELECT v.name, v.description, v.category, l.id, v.lat, v.lng, v.price
FROM (VALUES
          ('Biserica Neagră',   'Biserică gotică din centrul istoric al Brașovului.', 'RELIGIOUS', 'Brașov',      45.6409, 25.5881, 25.00),
          ('Muntele Tâmpa',     'Munte în mijlocul orașului, cu priveliște spre centru.', 'NATURE', 'Brașov',     45.6366, 25.5940, NULL),
          ('Castelul Bran',     'Castel medieval asociat cu legenda lui Dracula.',   'CASTLE',    'Bran',        45.5149, 25.3672, 70.00),
          ('Muzeul ASTRA',      'Muzeu în aer liber al civilizației populare.',      'MUSEUM',    'Sibiu',       45.7486, 24.1186, 35.00),
          ('Podul Minciunilor', 'Pod de fontă din centrul istoric al Sibiului.',     'MONUMENT',  'Sibiu',       45.7975, 24.1520, NULL),
          ('Grădina Botanică',  'Grădina botanică a Universității Babeș-Bolyai.',    'PARK',      'Cluj-Napoca', 46.7620, 23.5885, 20.00)
     ) AS v(name, description, category, location_name, lat, lng, price)
         JOIN locations l ON l.name = v.location_name AND l.country = 'România';
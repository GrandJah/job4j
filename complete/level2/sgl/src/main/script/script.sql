--select * from users where login='flank';
SELECT * FROM items JOIN users ON items.create_user = users.id;
SELECT * FROM items INNER JOIN users ON items.create_user = users.id;

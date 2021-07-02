truncate users, documents, clicks cascade;


insert into users (id, name)
select	i, 'User' || i 
from generate_series(1, 1000) as seq(i);


insert into documents (id, name)
select	i, 'Doc' || i 
from generate_series(1, 1000) as seq(i);


create or replace function random_user() returns UUID language sql as 
$$ 
SELECT uuid FROM users WHERE id > random()*1000 limit 1
$$;

create or replace function random_document() returns UUID language sql as 
$$
SELECT uuid FROM documents WHERE id > random()*1000 limit 1
$$;


insert into clicks (usr, doc, date)
select  random_user(),
        random_document(),
        NOW() 
from generate_series(1, 1000000) as seq(i);
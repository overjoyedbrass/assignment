truncate users, documents, clicks cascade;


insert into users (id, name)
select	i, 'User' || i 
from generate_series(1, 100000) as seq(i);


insert into documents (id, name)
select	i, 'Doc' || i 
from generate_series(1, 100000) as seq(i);


create or replace function random_user() returns UUID language sql as 
$$ 
SELECT uuid FROM users TABLESAMPLE SYSTEM_ROWS(1)
$$;

create or replace function random_document() returns UUID language sql as 
$$
SELECT uuid FROM documents TABLESAMPLE SYSTEM_ROWS(1)
$$;


insert into clicks (usr, doc, date)
select  random_user(),
        random_document(),
        NOW() 
from generate_series(1, 1000000) as seq(i);

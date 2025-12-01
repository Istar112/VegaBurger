
create table dependiente(
    id int,
    name varchar(50) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    is_admin boolean not null,
    img_path varchar(255) not null,
    activo boolean default false not null,
    primary key(id)
);

create table pedido(
    id int,
    id_dependiente int,
    np_totales int not null,
    nombre_usuario varchar(50) not null,
    importe_total decimal(10,2) not null,
    np_pendiente_entrega int not null,
    primary key(id),
    foreign key(id_dependiente) references dependiente(id) on delete cascade
);

create table categoria(
    id int,
    nombre varchar(50) not null,
    activar boolean not null,
    primary key(id)
);
create table producto(
    id int,
    id_categoria int not null,
    nombre varchar(50) not null,
    precio decimal(10,2) not null,
    pendiente_entrega boolean not null,
    descripcion varchar(255),
    img_path varchar(255),
    primary key(id),
    foreign key(id_categoria) references categoria(id) on delete cascade
);

create table linea_pedido(
    id int,
    id_producto int,
    id_pedido int,
    unidades int not null,
    precio decimal(10,2) not null,
    primary key(id),
    foreign key(id_producto) references producto(id) on delete cascade,
    foreign key(id_pedido) references pedido(id) on delete cascade
);


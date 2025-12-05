create table dependiente(
    id varchar(50),
    name varchar(50) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    image_path varchar(255) not null,
    enabled boolean default false not null,
    is_admin boolean not null,
    primary key(id)
);

create table pedido(
    id varchar(50) not null,
    id_dependiente varchar(50) not null,
    np_totales int not null,
    nombre_usuario varchar(50) not null,
    importe_total decimal(10,2) not null,
    np_pendiente_entrega int not null,
    primary key(id),
    foreign key(id_dependiente) references dependiente(id) on delete cascade
);

create table categoria(
    id varchar(50) not null,
    nombre varchar(50) not null,
    img_path varchar(255),
    activar boolean not null,
    primary key(id)
);
create table producto(
    id varchar(50) not null,
    id_categoria varchar(50) null,
    nombre varchar(50) not null,
    precio decimal(10,2) not null,
    pendiente_entrega boolean not null,
    descripcion varchar(255),
    img_path varchar(255),
    activar boolean default false,
    primary key(id),
    foreign key(id_categoria) references categoria(id) on delete set null
);

create table linea_pedido(
    id varchar(50) not null,
    id_producto varchar(50),
    id_pedido varchar(50),
    unidades int not null,
    precio decimal(10,2) not null,
    primary key(id),
    foreign key(id_producto) references producto(id) on delete cascade,
    foreign key(id_pedido) references pedido(id) on delete cascade
);


use ecommercedb;

insert into roles(name, description)
values ("ADMIN", "ADMIN");
insert into roles(name, description)
values ("USER", "USER");

insert into category(name, description, image)
values ("Jean", "Jean","https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/102021/1634891848905.jpeg");
insert into category(name, description, image)
values ("Short", "Short","https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/102021/1634891848905.jpeg");
insert into category(name, description, image)
values ("Shirt", "Shirt","https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/102021/1634891848905.jpeg");
insert into category(name, description, image)
values ("Hat", "hAT","https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/102021/1634891848905.jpeg");

insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("RAGLAN LINEN SHIRT", "RAGLAN LINEN SHIRT", "RAGLAN LINEN SHIRT", "RAGLAN LINEN SHIRT", 10, 449000, "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/052022/1651652873702.jpeg", false, 2, 0);
insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("LAPLACE SHIRT", "LAPLACE SHIRT", "LAPLACE SHIRT", "LAPLACE SHIRT", 5, 499000, "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/042022/1651047406216.jpeg", false, 3, 0);

insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("SPAGHETTI STRAP DRESS", "SPAGHETTI STRAP DRESS", "SPAGHETTI STRAP DRESS", "SPAGHETTI STRAP DRESS", 20, 350000, "https://cdn.ssstutter.com/products/system/102021/1634091331064.jpeg", false, 1, 0);
insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("RODEB DRESS", "RODEB DRESS", "RODEB DRESS", "RODEB DRESS", 13, 299000, "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/102021/1634891848905.jpeg", false, 1, 0);

insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("OVER PANTS", "OVER PANTS", "OVER PANTS", "OVER PANTS", 25, 499000, "https://cdn.ssstutter.com/products/system/102021/1634092105918.jpeg", false, 2, 0);
insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("ELASTIC PANTS", "ELASTIC PANTS", "ELASTIC PANTS", "ELASTIC PANTS", 22, 600000, "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/042022/1650967889691.jpeg", false, 2, 0);

insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("ELASTIC SHORTS", "ELASTIC SHORTS", "ELASTIC SHORTS", "ELASTIC SHORTS", 3, 199000, "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/042022/1650967690484.jpeg", false, 4, 0);
insert into product(name, alias, short_description, full_description, quantity, price, main_image, deleted, category_id, average_rating)
values ("ANCHOR SHORTS", "ANCHOR SHORTS", "ANCHOR SHORTS", "ANCHOR SHORTS", 52, 441000, "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/022022/1645504587992.jpeg", false, 4, 0);
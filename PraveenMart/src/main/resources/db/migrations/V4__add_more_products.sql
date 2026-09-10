-- V4: Add 15 additional catalog products across Fashion, Electronics, Home, and Accessories
INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Smart LED Desk Lamp with Wireless Qi Charger', 'Architectural dimmable LED task lamp with touch slider, 5 color modes, USB-C port, and 15W Qi wireless charging base.', 1299.00, 24, 'Electronics', 'https://images.unsplash.com/photo-1534073828943-f801091bb18c?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Smart LED Desk Lamp with Wireless Qi Charger');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Ultra-Fast 1TB Portable External SSD', 'Rugged USB 3.2 Gen 2 aluminum pocket solid state drive with up to 1050MB/s transfer speeds and hardware encryption.', 2499.00, 15, 'Electronics', 'https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Ultra-Fast 1TB Portable External SSD');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Dual-Band Wi-Fi 6 Gigabit Mesh Router', 'Next-gen Wi-Fi 6 wireless router with 4 high-gain antennas, WPA3 security, OFDMA, and seamless whole-home coverage.', 1599.00, 18, 'Electronics', 'https://images.unsplash.com/photo-1544197150-b99a580bb7a8?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Dual-Band Wi-Fi 6 Gigabit Mesh Router');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Magnetic Car Phone Mount with 15W MagSafe Charging', 'Vent-mounted aerospace alloy phone holder with 360-degree rotation and auto-aligning magnetic fast charging.', 499.00, 40, 'Electronics', 'https://images.unsplash.com/photo-1586105251261-72a756497a11?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Magnetic Car Phone Mount with 15W MagSafe Charging');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Oxford Button-Down Chambray Dress Shirt', 'Tailored cotton chambray shirt featuring mother-of-pearl buttons, button-down collar, and reinforced box pleat.', 749.00, 32, 'Fashion & Style', 'https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Oxford Button-Down Chambray Dress Shirt');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Handcrafted Italian Suede Penny Loafers', 'Classic slip-on penny loafers in supple vegetable-dyed suede with cushioned calfskin insole and rubber outsole.', 1899.00, 14, 'Fashion & Style', 'https://images.unsplash.com/photo-1533867617858-e7b97e060509?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Handcrafted Italian Suede Penny Loafers');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Double-Breasted Heritage Trench Coat', 'Water-resistant gabardine cotton trench coat with raglan sleeves, storm flap, horn buttons, and waist belt.', 2299.00, 10, 'Fashion & Style', 'https://images.unsplash.com/photo-1544441893-675973e31985?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Double-Breasted Heritage Trench Coat');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Japanese Selvedge Raw Denim 14oz Jeans', 'Narrow loom shuttle-woven raw indigo selvedge denim jeans with copper rivets and button fly.', 1399.00, 20, 'Fashion & Style', 'https://images.unsplash.com/photo-1542272604-780c96856592?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Japanese Selvedge Raw Denim 14oz Jeans');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Enameled Cast Iron Dutch Oven 5.5 Qt', 'Heavy-duty cast iron round Dutch oven with vibrant porcelain enamel exterior, tight-fitting lid, and stainless steel knob.', 2199.00, 12, 'Home & Kitchen', 'https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Enameled Cast Iron Dutch Oven 5.5 Qt');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Electric Gooseneck Variable Temperature Kettle', 'Matte black stainless steel pour-over kettle with 1-degree precision digital temp dial, LCD display, and 1h hold.', 1299.00, 16, 'Home & Kitchen', 'https://images.unsplash.com/photo-1517256064527-09c73fc73e38?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Electric Gooseneck Variable Temperature Kettle');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Organic Turkish Cotton Bath Sheet Set (2 Pcs)', 'Ultra-absorbent 700 GSM combed long-staple Turkish cotton oversized bath towels with ribbed border.', 849.00, 28, 'Home & Kitchen', 'https://images.unsplash.com/photo-1616046229478-9901c5536a45?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Organic Turkish Cotton Bath Sheet Set (2 Pcs)');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Artisan Acacia Wood Salad Bowl with Servers', 'Hand-turned natural acacia hardwood large salad serving bowl with matching ergonomic serving spoons.', 599.00, 22, 'Home & Kitchen', 'https://images.unsplash.com/photo-1584269600464-37b1b58a9fe7?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Artisan Acacia Wood Salad Bowl with Servers');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Aircraft Aluminum Carry-On Spinner Suitcase', 'Hard-shell anodized aluminum 20-inch carry-on luggage with dual TSA combination locks and silent 360 wheels.', 3499.00, 8, 'Accessories', 'https://images.unsplash.com/photo-1565026057447-bc90a3dceb87?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Aircraft Aluminum Carry-On Spinner Suitcase');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Natural Cork & Rubber Non-Slip Fitness Mat', 'Eco-friendly sustainable cork surface with natural tree rubber base for superior wet and dry grip yoga and pilates.', 699.00, 25, 'Accessories', 'https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Natural Cork & Rubber Non-Slip Fitness Mat');

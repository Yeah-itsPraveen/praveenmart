-- V3: Add more catalog products if not already present
INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Pure Mulberry Silk Scarf', 'Hand-woven 100% pure mulberry silk scarf with delicate floral hand-painted motifs and hand-rolled edges.', 1199.00, 25, 'Fashion & Style', 'https://images.unsplash.com/photo-1601924994987-69e26d50dc26?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Pure Mulberry Silk Scarf');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Merino Wool Cable-Knit Sweater', 'Chunky knit crewneck sweater crafted from ultra-soft Australian Merino wool for natural warmth.', 1499.00, 18, 'Fashion & Style', 'https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Merino Wool Cable-Knit Sweater');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Linen Casual Chino Trousers', 'Lightweight breathable linen blend relaxed fit trousers with drawstring waist and deep side pockets.', 699.00, 30, 'Fashion & Style', 'https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Linen Casual Chino Trousers');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Ultra-Slim Wireless Power Bank 10000mAh', '20W fast-charging magnetic wireless power bank with aluminum unibody and digital battery display.', 799.00, 35, 'Electronics', 'https://images.unsplash.com/photo-1609592426868-b76504a79c94?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Ultra-Slim Wireless Power Bank 10000mAh');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), '4K Ultra HD Webcam with Ring Light', 'Autofocus 60FPS streaming webcam with adjustable color temperature ring light and dual stereo mics.', 1199.00, 20, 'Electronics', 'https://images.unsplash.com/photo-1588508065123-287b28e013da?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '4K Ultra HD Webcam with Ring Light');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'True Wireless Earbuds with ANC', 'Ergonomic in-ear buds with hybrid active noise cancellation, transparency mode, and 32h playback.', 899.00, 40, 'Electronics', 'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'True Wireless Earbuds with ANC');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Portable RGB Gaming Soundbar', 'Compact desktop soundbar with dynamic reactive RGB backlighting, Bluetooth 5.3, and dual aux.', 599.00, 28, 'Electronics', 'https://images.unsplash.com/photo-1545454675-3531b543be5d?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Portable RGB Gaming Soundbar');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Handmade Stoneware Dinner Set (12 Pcs)', 'Artisanal matte-glazed ceramic dinner plates, salad plates, and bowls fired at high temperatures.', 1899.00, 12, 'Home & Kitchen', 'https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Handmade Stoneware Dinner Set (12 Pcs)');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Cold Brew Iced Coffee Maker 1L', 'Borosilicate glass pitcher with extra-fine stainless steel mesh infuser and airtight silicone seal.', 449.00, 22, 'Home & Kitchen', 'https://images.unsplash.com/photo-1517256064527-09c73fc73e38?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Cold Brew Iced Coffee Maker 1L');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Aromatherapy Ultrasonic Diffuser', 'Natural wood grain finish ultrasonic essential oil diffuser with 7 ambient LED mood lighting colors.', 399.00, 32, 'Home & Kitchen', 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Aromatherapy Ultrasonic Diffuser');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Culinary Stainless Steel Chef Knife 8 Inch', 'High carbon German stainless steel kitchen knife with precision forged edge and Pakkawood handle.', 749.00, 16, 'Home & Kitchen', 'https://images.unsplash.com/photo-1593618998160-e34014e67546?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Culinary Stainless Steel Chef Knife 8 Inch');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Polarized Sport Wrap Sunglasses', 'Ultra-durable TR90 frame with UV400 mirror polarized shatterproof lenses for cycling and running.', 349.00, 45, 'Accessories', 'https://images.unsplash.com/photo-1508296695146-257a814070b4?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Polarized Sport Wrap Sunglasses');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Waterproof Laptop Backpack 25L', 'Anti-theft expandable travel backpack with padded laptop sleeve, USB charging port, and water-repellent shell.', 999.00, 25, 'Accessories', 'https://images.unsplash.com/photo-1622560480605-d83c853bc5c3?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Waterproof Laptop Backpack 25L');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Genuine Leather Smartwatch Band', 'Hand-stitched top-grain vintage leather strap with brushed stainless steel buckle for 20mm/22mm watches.', 399.00, 30, 'Accessories', 'https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Genuine Leather Smartwatch Band');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT (SELECT id FROM users WHERE email = 'admin@praveenmart.com'), 'Thermal Insulated Travel Flask 750ml', 'Double-wall vacuum insulated stainless steel water bottle keeping beverages hot for 12h and cold for 24h.', 299.00, 50, 'Accessories', 'https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop&q=80'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Thermal Insulated Travel Flask 750ml');

-- V5: Remove mismatched products reported in UI (broccoli and rubber duck images)
DELETE FROM cart_items WHERE product_id IN (SELECT id FROM products WHERE name IN ('Artisanal Teak Wood Serving Board', 'Minimalist Titanium Key Holder Organizer'));
DELETE FROM wishlist_items WHERE product_id IN (SELECT id FROM products WHERE name IN ('Artisanal Teak Wood Serving Board', 'Minimalist Titanium Key Holder Organizer'));
DELETE FROM reviews WHERE product_id IN (SELECT id FROM products WHERE name IN ('Artisanal Teak Wood Serving Board', 'Minimalist Titanium Key Holder Organizer'));
DELETE FROM order_items WHERE product_id IN (SELECT id FROM products WHERE name IN ('Artisanal Teak Wood Serving Board', 'Minimalist Titanium Key Holder Organizer'));
DELETE FROM products WHERE name IN ('Artisanal Teak Wood Serving Board', 'Minimalist Titanium Key Holder Organizer');

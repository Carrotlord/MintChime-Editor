bit_field = 0
delta_t = 1.0/64.0
tracker = 0.0
incr_by = 1
while true
    bit_field += incr_by
    puts bit_field.to_s(2).gsub("1", "@").gsub("0", "_")
    sleep(delta_t * 1.28)
    tracker += delta_t
    if tracker > 4.0 and bit_field < 100000
        tracker = 0.0
        incr_by *= 1 + rand(7)
    elsif tracker > 4.0
        tracker = 0.0
        bit_field += rand(-100 .. 113)
        bit_field *= 1 + rand(0.1 .. 1.97913457639843)
        bit_field = bit_field.floor
    end
end

function get_global(unused, name)
    return _G[name]
end

function unnil(unused, fn, ...)
    return fn(...)
end
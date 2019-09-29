function lua_io_popen(command,mode)
    return io.popen(command,mode)
end

function lua_io_open(file,mode)
    return io.open(file,mode)
end
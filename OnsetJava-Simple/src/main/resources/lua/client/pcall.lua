  function pcall(fn, ...)
    r1, r2, r3, r4, r5, r6, r7, r8 = fn(...)
    return true, r1, r2, r3, r4, r5, r6, r7, r8
end
/** @noSelfInFile */

declare class LuaFileHandle {
    lines(): () => string | undefined;

    write(data: string): void;

    flush(): void;

    close(): void;
}

declare function lua_io_popen(command: string, mode: string): LuaFileHandle;

declare function lua_io_open(fileName: string, mode: string): LuaFileHandle;
/** @noSelfInFile */

namespace OnsetJavaServer {
    export class ActionAdapter {
        private handle: LuaFileHandle;
        private readHandle?: LuaFileHandle = undefined;
        private lastIdentifier = 0;

        constructor(command: string, private listener: (type: string, nonce: number, params: any[]) => void) {
            this.handle = lua_io_popen(command + " > out", "w");
            while (this.readHandle === undefined) {
                this.readHandle = lua_io_open("out", "r");
            }
            AddEvent("OnGameTick", () => {
                this.tick();
            });
        }

        private tick(): void {
            let lines: string[] = [];
            let iter = this.readHandle!!.lines();
            while (true) {
                let line = iter();
                if (line === undefined) {
                    break;
                }
                lines.push(line);
                for (let line of lines) {
                    if (line.length === 0) {
                        continue;
                    }
                    if (!line.startsWith("{")) {
                        continue;
                    }
                    let json = JSON.parse(line);
                    if (json.identifier <= this.lastIdentifier) {
                        continue;
                    }
                    this.lastIdentifier = json.identifier;
                    this.listener(json.type, json.nonce, json.params);
                }
            }
        }

        public call(type: string, nonce: number, params: any[]): void {
            let text = JSON.stringify({
                type: type,
                nonce: nonce,
                params: params
            }) + "\n";
            this.handle.write(text);
            this.handle.flush();
        }
    }
}
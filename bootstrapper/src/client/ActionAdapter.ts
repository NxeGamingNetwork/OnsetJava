/** @noSelfInFile */

namespace OnsetJavaClient {
    export class ActionAdapter {
        constructor(private listener: (type: string, nonce: number, params: any[]) => void) {
            AddRemoteEvent("Action", (line: string) => {
                let json = JSON.parse(line);
                this.listener(json.type, json.nonce, json.params);
            });
        }

        public call(type: string, nonce: number, params: any[]): void {
            let text = JSON.stringify({
                type: type,
                nonce: nonce,
                params: params
            });
            CallRemoteEvent("Action", text);
        }
    }
}
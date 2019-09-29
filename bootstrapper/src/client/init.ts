/** @noSelfInFile */

namespace OnsetJavaClient {
    let adapter: ActionAdapter;
    let keys: string[] = [];
    adapter = new ActionAdapter((type, nonce, params) => {
        if (type === "Call") {
            let ret = pcall_array(get_global(params[0]), params.splice(1));
            adapter.call("Return", nonce, ret);
        }
        if (type === "RegisterEvents") {
            for (let name of params[0]) {
                registerEvent(name);
            }
        }
        if (type === "RegisterKeys") {
            registerKeys(params[0]);
        }
    });

    function registerKeys(newKeys: string[]) {
        if (keys.length === 0) {
            AddEvent("OnKeyPress", (key: string) => {
                if (keys.indexOf(key) > -1) {
                    adapter.call("OnKeyPress", 0, [key]);
                }
            });
            AddEvent("OnKeyRelease", (key: string) => {
                if (keys.indexOf(key) > -1) {
                    adapter.call("OnKeyRelease", 0, [key]);
                }
            });
        }
        for (let key of newKeys) {
            if (keys.indexOf(key) < 0) {
                keys.push(key);
            }
        }
    }

    function registerEvent(name: string) {
        AddEvent(name, (...params: any[]) => {
            adapter.call(name, 0, params);
        });
    }
}


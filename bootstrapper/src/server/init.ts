/** @noSelfInFile */

declare function load(code: string): [()=>void,string];

namespace OnsetJavaServer {
    let adapter: ActionAdapter;
    adapter = new ActionAdapter("java -jar D:\\dev\\General\\OnsetJavaAPI\\target\\OnsetJavaAPI-1.0.jar", (type, nonce, params) => {
        if(type === "Execute"){
            let result = load(params[0]);
            pcall(result[0]);
        }
        if(type === "Call"){
            let ret = pcall_array(get_global(params[0]), params.splice(1));
            adapter.call("Return", nonce, ret);
        }
        if(type === "RegisterEvents"){
            for(let name of params[0]){
                registerEvent(name);
            }
        }
        if(type === "RegisterCommand"){
            for(let name of params[0]){
                registerCommand(name);
            }
        }
        if(type === "Forward"){
            CallRemoteEvent(params[0], "Action", params[1]);
        }
    });

    AddRemoteEvent("Action", (playerId: number, data: string) => {
        adapter.call("Forward", 0, [playerId, data]);
    });

    function registerEvent(name: string) {
        AddEvent(name, (...params: any[]) => {
            adapter.call(name, 0, params);
        });
    }

    function registerCommand(name: string) {
        AddCommand(name, (...params: any[]) => {
            params.unshift(name);
            adapter.call("Command", 0, params);
        });
    }
    
}
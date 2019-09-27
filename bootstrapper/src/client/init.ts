/** @noSelfInFile */

namespace OnsetJavaClient {
    let adapter: ActionAdapter;
    adapter = new ActionAdapter((type, nonce, params) => {
        if(type === "Call"){
            let ret = pcall_array(get_global(params[0]), params.splice(1));
            adapter.call("Return", nonce, ret);
        }
        if(type === "RegisterEvents"){
            for(let name of params[0]){
                registerEvent(name);
            }
        }
    });

    function registerEvent(name: string) {
        AddEvent(name, (...params: any[]) => {
            adapter.call(name, 0, params);
        });
    }
}


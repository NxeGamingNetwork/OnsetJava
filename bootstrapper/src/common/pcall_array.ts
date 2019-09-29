/** @noSelfInFile */

/** @tupleReturn */
declare function pcall(fn: Function, ...params: any[]): [boolean, any, any, any, any, any, any, any, any];

/** @tupleReturn */
declare function unnil(fn: Function, ...params: any[]): [boolean, any, any, any, any, any, any, any, any];

declare function get_global(name: string): any;

function pcall_array_raw(fn: Function, params: any[]): [boolean, any, any, any, any, any, any, any, any] {
    switch (params.length) {
        case 1:
            return unnil(pcall, fn, params[0]);
        case 2:
            return unnil(pcall, fn, params[0], params[1]);
        case 3:
            return unnil(pcall, fn, params[0], params[1], params[2]);
        case 4:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3]);
        case 5:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3], params[4]);
        case 6:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3], params[4], params[5]);
        case 7:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        case 8:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        case 9:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
        case 10:
            return unnil(pcall, fn, params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);
    }
    return unnil(pcall, fn);
};

function pcall_array(fn: Function, params: any[]): [any, any, any, any, any, any, any, any] {
    let ret = pcall_array_raw(fn, params);
    return [ret[1], ret[2], ret[3], ret[4], ret[5], ret[6], ret[7], ret[8]];
}
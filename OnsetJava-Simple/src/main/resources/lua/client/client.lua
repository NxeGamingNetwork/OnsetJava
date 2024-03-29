function pcall_array_raw(self, fn, params)
    local ____switch2 = #params
    if ____switch2 == 1 then
        goto ____switch2_case_0
    end
    if ____switch2 == 2 then
        goto ____switch2_case_1
    end
    if ____switch2 == 3 then
        goto ____switch2_case_2
    end
    if ____switch2 == 4 then
        goto ____switch2_case_3
    end
    if ____switch2 == 5 then
        goto ____switch2_case_4
    end
    if ____switch2 == 6 then
        goto ____switch2_case_5
    end
    if ____switch2 == 7 then
        goto ____switch2_case_6
    end
    if ____switch2 == 8 then
        goto ____switch2_case_7
    end
    if ____switch2 == 9 then
        goto ____switch2_case_8
    end
    if ____switch2 == 10 then
        goto ____switch2_case_9
    end
    goto ____switch2_end
    ::____switch2_case_0::
    do
        return ({
            unnil(nil, pcall, fn, params[1])
        })
    end
    ::____switch2_case_1::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2])
        })
    end
    ::____switch2_case_2::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3])
        })
    end
    ::____switch2_case_3::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4])
        })
    end
    ::____switch2_case_4::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4], params[5])
        })
    end
    ::____switch2_case_5::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4], params[5], params[6])
        })
    end
    ::____switch2_case_6::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4], params[5], params[6], params[7])
        })
    end
    ::____switch2_case_7::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8])
        })
    end
    ::____switch2_case_8::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9])
        })
    end
    ::____switch2_case_9::
    do
        return ({
            unnil(nil, pcall, fn, params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10])
        })
    end
    ::____switch2_end::
    return ({
        unnil(nil, pcall, fn)
    })
end
function pcall_array(self, fn, params)
    local ret = pcall_array_raw(nil, fn, params)
    return {ret[2], ret[3], ret[4], ret[5], ret[6], ret[7], ret[8], ret[9]}
end
OnsetJavaClient = OnsetJavaClient or {}
do
    OnsetJavaClient.ActionAdapter = {}
    local ActionAdapter = OnsetJavaClient.ActionAdapter
    ActionAdapter.name = "ActionAdapter"
    ActionAdapter.__index = ActionAdapter
    ActionAdapter.prototype = {}
    ActionAdapter.prototype.__index = ActionAdapter.prototype
    ActionAdapter.prototype.constructor = ActionAdapter
    function ActionAdapter.new(...)
        local self = setmetatable({}, ActionAdapter.prototype)
        self:____constructor(...)
        return self
    end
    function ActionAdapter.prototype.____constructor(self, listener)
        self.listener = listener
        AddRemoteEvent(
            "Action",
            function(line)
                local json = JSON:parse(line)
                self.listener(json.type, json.nonce, json.params)
            end
        )
    end
    function ActionAdapter.prototype.call(self, ____type, nonce, params)
        local text = JSON:stringify({type = ____type, nonce = nonce, params = params})
        CallRemoteEvent("Action", text)
    end
end
function __TS__ArrayPush(arr, ...)
    local items = ({...})
    for ____, item in ipairs(items) do
        arr[#arr + 1] = item
    end
    return #arr
end
local ____symbolMetatable = {
    __tostring = function(self)
        if self.description == nil then
            return "Symbol()"
        else
            return "Symbol(" .. tostring(self.description) .. ")"
        end
    end
}
function __TS__Symbol(description)
    return setmetatable({description = description}, ____symbolMetatable)
end
Symbol = {
    iterator = __TS__Symbol("Symbol.iterator"),
    hasInstance = __TS__Symbol("Symbol.hasInstance"),
    species = __TS__Symbol("Symbol.species"),
    toStringTag = __TS__Symbol("Symbol.toStringTag")
}
function __TS__Iterator(iterable)
    if iterable[Symbol.iterator] then
        local iterator = iterable[Symbol.iterator](iterable)
        return function()
            local result = iterator:next()
            if not result.done then
                return result.value
            else
                return nil
            end
        end
    else
        local i = 0
        return function()
            i = i + 1
            return iterable[i]
        end
    end
end
function __TS__ArrayIndexOf(arr, searchElement, fromIndex)
    local len = #arr
    if len == 0 then
        return -1
    end
    local n = 0
    if fromIndex then
        n = fromIndex
    end
    if n >= len then
        return -1
    end
    local k
    if n >= 0 then
        k = n
    else
        k = len + n
        if k < 0 then
            k = 0
        end
    end
    do
        local i = k
        while i < len do
            if arr[i + 1] == searchElement then
                return i
            end
            i = i + 1
        end
    end
    return -1
end
OnsetJavaClient = OnsetJavaClient or {}
do
    local adapter, keys, registerKeys, registerEvent
    function registerKeys(newKeys)
        if #keys == 0 then
            AddEvent(
                "OnKeyPress",
                function(key)
                    if __TS__ArrayIndexOf(keys, key) > -1 then
                        adapter:call(
                            "OnKeyPress",
                            0,
                            {
                                key,
                                IsCtrlPressed(),
                                IsShiftPressed(),
                                IsCmdPressed(),
                                IsAltPressed()
                            }
                        )
                    end
                end
            )
            AddEvent(
                "OnKeyRelease",
                function(key)
                    if __TS__ArrayIndexOf(keys, key) > -1 then
                        adapter:call(
                            "OnKeyRelease",
                            0,
                            {
                                key,
                                IsCtrlPressed(),
                                IsShiftPressed(),
                                IsCmdPressed(),
                                IsAltPressed()
                            }
                        )
                    end
                end
            )
        end
        for ____, key in ipairs(newKeys) do
            if __TS__ArrayIndexOf(keys, key) < 0 then
                __TS__ArrayPush(keys, key)
            end
        end
    end
    function registerEvent(name)
        AddEvent(
            name,
            function(...)
                local params = ({...})
                if name == "OnPlayerStartEnterVehicle" then
                    adapter:call(name, 0, {params[1], -1})
                    return false
                end
                adapter:call(name, 0, params)
                if name == "OnPlayerStartExitVehicle" then
                    return false
                end
            end
        )
    end
    keys = {}
    adapter = OnsetJavaClient.ActionAdapter.new(
        function(____type, nonce, params)
            if ____type == "Call" then
                local parr = {}
                if #params > 1 then
                    do
                        local i = 1
                        while i < #params do
                            __TS__ArrayPush(parr, params[i + 1])
                            i = i + 1
                        end
                    end
                end
                local ret = pcall_array(
                    nil,
                    get_global(nil, params[1]),
                    parr
                )
                adapter:call("Return", nonce, ret)
            end
            if ____type == "RegisterEvents" then
                for name in __TS__Iterator(params[1]) do
                    registerEvent(name)
                end
            end
            if ____type == "RegisterKeys" then
                registerKeys(params[1])
            end
        end
    )
end

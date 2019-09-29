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
function __TS__ArrayPush(arr, ...)
    local items = ({...})
    for ____, item in ipairs(items) do
        arr[#arr + 1] = item
    end
    return #arr
end
function __TS__StringStartsWith(self, searchString, position)
    if position == nil or position < 0 then
        position = 0
    end
    return string.sub(self, position + 1, #searchString + position) == searchString
end
OnsetJavaServer = OnsetJavaServer or {}
do
    OnsetJavaServer.ActionAdapter = {}
    local ActionAdapter = OnsetJavaServer.ActionAdapter
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
    function ActionAdapter.prototype.____constructor(self, command, listener)
        self.readHandle = nil
        self.lastIdentifier = 0
        self.listener = listener
        self.handle = lua_io_popen(
            tostring(command) .. " > out",
            "w"
        )
        while self.readHandle == nil do
            self.readHandle = lua_io_open("out", "r")
        end
        AddEvent(
            "OnGameTick",
            function()
                self:tick()
            end
        )
    end
    function ActionAdapter.prototype.tick(self)
        local lines = {}
        local iter
        iter = self.readHandle:lines()
        while true do
            local line = iter()
            if line == nil then
                break
            end
            __TS__ArrayPush(lines, line)
            for ____, line in ipairs(lines) do
                do
                    if #line == 0 then
                        goto __continue8
                    end
                    if not __TS__StringStartsWith(line, "{") then
                        goto __continue8
                    end
                    local json = JSON:parse(line)
                    if json.identifier <= self.lastIdentifier then
                        goto __continue8
                    end
                    self.lastIdentifier = json.identifier
                    self.listener(json.type, json.nonce, json.params)
                end
                ::__continue8::
            end
        end
    end
    function ActionAdapter.prototype.call(self, ____type, nonce, params)
        local text = tostring(
            JSON:stringify({type = ____type, nonce = nonce, params = params})
        ) .. "\n"
        self.handle:write(text)
        self.handle:flush()
    end
end
function __TS__ArraySplice(list, start, deleteCount, ...)
    local items = ({...})
    local len = #list
    local actualStart
    if start < 0 then
        actualStart = math.max(len + start, 0)
    else
        actualStart = math.min(start, len)
    end
    local itemCount = #items
    local actualDeleteCount
    if not start then
        actualDeleteCount = 0
    elseif not deleteCount then
        actualDeleteCount = len - actualStart
    else
        actualDeleteCount = math.min(
            math.max(deleteCount, 0),
            len - actualStart
        )
    end
    local out = {}
    do
        local k = 0
        while k < actualDeleteCount do
            local from = actualStart + k
            if list[from + 1] then
                out[k + 1] = list[from + 1]
            end
            k = k + 1
        end
    end
    if itemCount < actualDeleteCount then
        do
            local k = actualStart
            while k < len - actualDeleteCount do
                local from = k + actualDeleteCount
                local to = k + itemCount
                if list[from + 1] then
                    list[to + 1] = list[from + 1]
                else
                    list[to + 1] = nil
                end
                k = k + 1
            end
        end
        do
            local k = len
            while k > len - actualDeleteCount + itemCount do
                list[k] = nil
                k = k - 1
            end
        end
    elseif itemCount > actualDeleteCount then
        do
            local k = len - actualDeleteCount
            while k > actualStart do
                local from = k + actualDeleteCount - 1
                local to = k + itemCount - 1
                if list[from + 1] then
                    list[to + 1] = list[from + 1]
                else
                    list[to + 1] = nil
                end
                k = k - 1
            end
        end
    end
    local j = actualStart
    for ____, e in ipairs(items) do
        list[j + 1] = e
        j = j + 1
    end
    do
        local k = #list - 1
        while k >= len - actualDeleteCount + itemCount do
            list[k + 1] = nil
            k = k - 1
        end
    end
    return out
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
function __TS__ArrayUnshift(arr, ...)
    local items = ({...})
    do
        local i = #items - 1
        while i >= 0 do
            table.insert(arr, 1, items[i + 1])
            i = i - 1
        end
    end
    return #arr
end
OnsetJavaServer = OnsetJavaServer or {}
do
    local adapter, registerEvent, registerCommand
    function registerEvent(name)
        AddEvent(
            name,
            function(...)
                local params = ({...})
                adapter:call(name, 0, params)
            end
        )
    end
    function registerCommand(name)
        AddCommand(
            name,
            function(...)
                local params = ({...})
                __TS__ArrayUnshift(params, name)
                adapter:call("Command", 0, params)
            end
        )
    end
    adapter = OnsetJavaServer.ActionAdapter.new(
        "java -jar OnsetJava-Simple-1.0.jar runtheserver",
        function(____type, nonce, params)
            if ____type == "Execute" then
                local result = load(params[1])
                pcall(nil, result[1])
            end
            if ____type == "Call" then
                local ret = pcall_array(
                    nil,
                    get_global(nil, params[1]),
                    __TS__ArraySplice(params, 1)
                )
                if params[1] == "GetPlayerSteamId" then
                    ret[1] = tostring(ret[1])
                end
                adapter:call("Return", nonce, ret)
            end
            if ____type == "RegisterEvents" then
                for name in __TS__Iterator(params[1]) do
                    registerEvent(name)
                end
            end
            if ____type == "RegisterCommand" then
                registerCommand(params[1])
            end
            if ____type == "Forward" then
                CallRemoteEvent(params[1], "Action", params[2])
            end
        end
    )
    AddRemoteEvent(
        "Action",
        function(playerId, data)
            adapter:call("Forward", 0, {playerId, data})
        end
    )
end

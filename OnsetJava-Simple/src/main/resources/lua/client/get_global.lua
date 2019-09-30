function get_global(unused, name)
    if _ENV[name] ~= nil then
        return _ENV[name]
    end
    if name == "CreateSound" then
        return CreateSound
    end
    if name == "CreateSound3D" then
        return CreateSound3D
    end
    if name == "CreateWebUI" then
        return CreateWebUI
    end
    if name == "CreateWebUI3D" then
        return CreateWebUI3D
    end
    if name == "DestroySound" then
        return DestroySound
    end
    if name == "DestroyWebUI" then
        return DestroyWebUI
    end
    if name == "ExecuteWebJS" then
        return ExecuteWebJS
    end
    if name == "SetWebURL" then
        return SetWebURL
    end
    if name == "SetWebAlignment" then
        return SetWebAlignment
    end
    if name == "SetWebAnchors" then
        return SetWebAnchors
    end
    if name == "SetWebVisibility" then
        return SetWebVisibility
    end
end
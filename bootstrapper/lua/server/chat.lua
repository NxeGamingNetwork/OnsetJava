AddEvent("OnPlayerChat", function(player, message)
    AddPlayerChatAll(GetPlayerName(player)..": "..message)
end)
REST API

Demande d'autorisation :    

    curl gedhhapp:mfsrvl@vps358243.ovh.net/gedhh/oauth/token -d "grant_type=password&username=superadmin&password=gedhhadmin"
    
Consulter les communes :

    Mettre le header "Authorization : Bearer <access_token>" précédemment récupéré
    http://vps358243.ovh.net/gedhh/communes/all
    
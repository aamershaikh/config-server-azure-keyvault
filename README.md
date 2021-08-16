**Sample configuration YML - to be commited in git and read by the config server**
my:
 greeting: Hello World

db:
 connection: connection-string-here
 host:127.0.0.1
 port:1200

spring:
 profiles:
  active: keyvault
 datasource:
  url: {connectionString}

**bootstrap properties added in application.yml - indicates the places to check in order to fetch the configuration**
  spring.cloud.config.server.git.uri=${HOME}/Documents/spring-cloud-config-server-configrepo
  azure.keyvault.client-id=6627b520-86fe-4b70-ad5b-e7d16f950736
  azure.keyvault.client-key=cAOsWFqKwODx_6ya0OU2F6jG9tsaqfwBAg
  azure.keyvault.enabled=true
  azure.keyvault.tenant-id=b10dd107-39c7-42bb-b863-cf30c9be1638
  azure.keyvault.uri=https://asdemokv.vault.azure.net/


Steps : 

**_Create AD_**

  {
    "appId": "6627b520-86fe-4b70-ad5b-e7d16f950736",
    "displayName": "as-adspdemo",
    "name": "6627b520-86fe-4b70-ad5b-e7d16f950736",
    "password": "cAOsWFqKwODx_6ya0OU2F6jG9tsaqfwBAg",
    "tenant": "b10dd107-39c7-42bb-b863-cf30c9be1638"
  }


**_Create RG_**

  {
  "id": "/subscriptions/5bd7f604-5fd6-4da2-ab30-943842bc2a41/resourceGroups/as-resgrp2",
  "location": "eastus",
  "managedBy": null,
  "name": "as-resgrp2",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null,
  "type": "Microsoft.Resources/resourceGroups"
}  


**_Create KV_**

az keyvault create \
    --resource-group as-resgrp2 \
    --name asdemokv \
    --enabled-for-deployment true \
    --enabled-for-disk-encryption true \
    --enabled-for-template-deployment true \
    --location eastus \
    --query properties.vaultUri \
    --sku standard

**_Configure KV to allow get and list operations_**
aamer@Azure:~$ az keyvault set-policy --name asdemokv --spn 6627b520-86fe-4b70-ad5b-e7d16f950736 --secret-permissions get list
    {
      "id": "/subscriptions/5bd7f604-5fd6-4da2-ab30-943842bc2a41/resourceGroups/as-resgrp2/providers/Microsoft.KeyVault/vaults/asdemokv",
      "location": "eastus",
      "name": "asdemokv",
      "properties": {
        "accessPolicies": [
          {
            "applicationId": null,
            "objectId": "3e9a8019-5d4e-40b4-b868-3febbcfed0e5",
            "permissions": {
              "certificates": [
                "get",
                "list",
                "delete",
                "create",
                "import",
                "update",
                "managecontacts",
                "getissuers",
                "listissuers",
                "setissuers",
                "deleteissuers",
                "manageissuers",
                "recover"
              ],
              "keys": [
                "get",
                "create",
                "delete",
                "list",
                "update",
                "import",
                "backup",
                "restore",
                "recover"
              ],
              "secrets": [
                "get",
                "list",
                "set",
                "delete",
                "backup",
                "restore",
                "recover"
              ],
              "storage": [
                "get",
                "list",
                "delete",
                "set",
                "update",
                "regeneratekey",
                "setsas",
                "listsas",
                "getsas",
                "deletesas"
              ]
            },
            "tenantId": "b10dd107-39c7-42bb-b863-cf30c9be1638"
          },
          {
            "applicationId": null,
            "objectId": "f673628a-69ba-41d6-8232-1e7ef9581e6b",
            "permissions": {
              "certificates": null,
              "keys": null,
              "secrets": [
                "list",
                "get"
              ],
              "storage": null
            },
            "tenantId": "b10dd107-39c7-42bb-b863-cf30c9be1638"
          }
        ],
        "createMode": null,
        "enablePurgeProtection": null,
        "enableRbacAuthorization": null,
        "enableSoftDelete": true,
        "enabledForDeployment": true,
        "enabledForDiskEncryption": true,
        "enabledForTemplateDeployment": true,
        "networkAcls": null,
        "privateEndpointConnections": null,
        "provisioningState": "Succeeded",
        "sku": {
          "family": "A",
          "name": "standard"
        },
        "softDeleteRetentionInDays": 90,
        "tenantId": "b10dd107-39c7-42bb-b863-cf30c9be1638",
        "vaultUri": "https://asdemokv.vault.azure.net/"
      },
      "resourceGroup": "as-resgrp2",
      "systemData": {
        "createdAt": "1970-01-19T20:27:17.958000+00:00",
        "createdBy": "aameronline@outlook.com",
        "createdByType": "User",
        "lastModifiedAt": "1970-01-19T20:27:18.708000+00:00",
        "lastModifiedBy": "aameronline@outlook.com",
        "lastModifiedByType": "User"
      },
      "tags": {},
      "type": "Microsoft.KeyVault/vaults"
    }  


**_Store key in the vault_**
    az keyvault secret set --name "connectionString" \
      --vault-name "asdemokv" \
      --value "jdbc:sqlserver://SERVER.database.windows.net:1433;database=DATABASE;"

    {
        "attributes": {
          "created": "2021-08-13T07:14:19+00:00",
          "enabled": true,
          "expires": null,
          "notBefore": null,
          "recoveryLevel": "Recoverable+Purgeable",
          "updated": "2021-08-13T07:14:19+00:00"
        },
        "contentType": null,
        "id": "https://asdemokv.vault.azure.net/secrets/connectionString/1ed5c058a7bb4ad7bb7c94fedfb6d390",
        "kid": null,
        "managed": null,
        "name": "connectionString",
        "tags": {
          "file-encoding": "utf-8"
        },
        "value": "jdbc:sqlserver://SERVER.database.windows.net:1433;database=DATABASE;"
      }  
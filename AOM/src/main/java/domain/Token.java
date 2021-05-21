package domain;

import net.sf.json.JSONObject;

public class Token {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", scope='" + scope + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(new Token(null));
        System.out.println(new Token("{a:1}"));
    }

    public Token(String tokenString){
        // TODO: 11/11/2020 what if the tokenString isnot a json object
        //  what if net.sf.json.JSONException JSONObject["access_token"] not found.
        JSONObject object = JSONObject.fromObject(tokenString);
        if (!object.isNullObject() && !object.isEmpty()) {
            this.access_token = object.getString("access_token");
            this.token_type = object.getString("token_type");
            this.refresh_token = object.getString("refresh_token");
            this.expires_in = Integer.parseInt(object.getString("expires_in"));
            this.scope = object.getString("scope");
        }
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

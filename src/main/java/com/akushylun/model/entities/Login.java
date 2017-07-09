package com.akushylun.model.entities;

public class Login {
    private int id;
    private String email;
    private String password;

    private Login(Builder builder) {
	this.id = builder.id;
	this.email = builder.email;
	this.password = builder.password;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public String getPassword() {
	return password;
    }

    public static class Builder {
	private int id;
	private String email;
	private String password;

	public Builder withId(int id) {
	    this.id = id;
	    return this;
	}

	public Builder withEmail(String email) {
	    this.email = email;
	    return this;
	}

	public Builder withPassword(String password) {
	    this.password = password;
	    return this;
	}

	public Login build() {
	    return new Login(this);
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + id;
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Login))
	    return false;
	Login other = (Login) obj;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (id != other.id)
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Login [id=" + id + ", email=" + email + ", password=" + password + "]";
    }

}

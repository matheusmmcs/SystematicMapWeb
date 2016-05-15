package br.com.ufpi.systematicmap.model.vo;

import br.com.ufpi.systematicmap.model.Alternative;
import br.com.ufpi.systematicmap.model.User;

public class UserAndAlternative {
    private User user;
    private Alternative alternative;

    public UserAndAlternative(User user, Alternative alternative) {
        this.user = user;
        this.alternative = alternative;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Alternative getAlternative() {
        return this.alternative;
    }

    public void setAlternative(Alternative alternative) {
        this.alternative = alternative;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alternative == null) ? 0 : alternative.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAndAlternative other = (UserAndAlternative) obj;
		if (alternative == null) {
			if (other.alternative != null)
				return false;
		} else if (!alternative.equals(other.alternative))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public String toString() {
        return "UserAndAlternative [user=" + (Object)this.user + ", alternative=" + (Object)this.alternative + "]";
    }
}


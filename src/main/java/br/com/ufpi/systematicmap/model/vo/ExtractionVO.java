package br.com.ufpi.systematicmap.model.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpi.systematicmap.model.Question;

public class ExtractionVO {
    private Question question;
    private List<UserAndAlternative> userAndAlternatives;

    public ExtractionVO(Question question) {
        this.question = question;
        this.userAndAlternatives = new ArrayList<UserAndAlternative>();
    }

    public ExtractionVO(Question question, List<UserAndAlternative> userAndAlternatives) {
        this.question = question;
        this.userAndAlternatives = userAndAlternatives;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<UserAndAlternative> getUserAndAlternatives() {
        return this.userAndAlternatives;
    }

    public void setUserAndAlternatives(ArrayList<UserAndAlternative> userAndAlternatives) {
        this.userAndAlternatives = userAndAlternatives;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
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
		ExtractionVO other = (ExtractionVO) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	public String toString() {
        return "ExtractionVO [question=" + this.question + ", userAndAlternatives=" + this.userAndAlternatives + "]";
    }
}


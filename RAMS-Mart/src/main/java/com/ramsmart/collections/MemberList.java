package main.java.com.ramsmart.collections;

import java.io.Serializable;
import java.util.ArrayList;

import main.java.com.ramsmart.entities.Member;

public class MemberList implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Member> members = new ArrayList<>();

	public Member search(int id) {
		for (Member member : members) {
			if (member.getMemberId() == id) {
				return member;
			}
		}

		return null;
	}

	public ArrayList<Member> searchMembersByName(String name) {
		ArrayList<Member> matchedMembers = new ArrayList<>();

		for (Member member : members) {
			if (member.getMemberName().toLowerCase().startsWith(name.toLowerCase())) {
				matchedMembers.add(member);
			}
		}

		return matchedMembers;
	}

	public boolean addMember(Member member) {
		return members.add(member);
	}

	public boolean deleteMember(int memberId) {
		Member member = search(memberId);

		if (member != null) {
			return members.remove(member);
		}

		return false;
	}

	public boolean contains(Member member) {
		return members.contains(member);
	}

	public boolean isEmpty() {
		return members.isEmpty();
	}

	/**
	 * This method overrides the toString method that displays the list of all the
	 * members.
	 */
	@Override
	public String toString() {
		StringBuilder display = new StringBuilder("------ Members ------\n");
		for (Member member : members) {
			display.append(member).append("\n");
		}
		return display.toString();
	}
}

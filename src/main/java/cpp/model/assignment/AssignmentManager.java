package cpp.model.assignment;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import cpp.commons.util.CollectionUtil;
import cpp.model.assignment.exceptions.AssignmentNotFoundException;
import cpp.model.assignment.exceptions.AssignmentNotSubmittedException;
import cpp.model.assignment.exceptions.ContactNotAssignedAssignmentException;

/**
 * Manages allocations of assignments to contacts and their per-contact state.
 */
public class AssignmentManager {

    private final Map<String, Set<ContactAssignment>> byAssignment;
    private final Map<String, Set<ContactAssignment>> byContact;

    /**
     * Creates an empty assignment manager with no assignments or contacts.
     */
    public AssignmentManager() {
        this.byAssignment = new HashMap<>();
        this.byContact = new HashMap<>();
    }

    /**
     * Creates an assignment manager with the given initial contact assignments. The
     * initial contact assignments must not contain duplicates.
     */
    public AssignmentManager(List<ContactAssignment> initialAssignments) {
        this.byAssignment = new HashMap<>();
        this.byContact = new HashMap<>();
        for (ContactAssignment ca : initialAssignments) {
            this.allocate(ca.getAssignmentId(), ca.getContactId());
            if (ca.isSubmitted()) {
                this.submit(ca.getAssignmentId(), ca.getContactId());
            }
            if (ca.isGraded()) {
                this.grade(ca.getAssignmentId(), ca.getContactId(), ca.getScore());
            }
        }
    }

    /**
     * Allocate an assignment to a contact that is not already allocated the
     * assignment.
     *
     * @param assignmentId the id of the assignment to allocate
     * @param contactId    the id of the contact to allocate to
     */
    public boolean allocate(String assignmentId, String contactId) {
        CollectionUtil.requireAllNonNull(assignmentId, contactId);
        Set<ContactAssignment> assSet = this.byAssignment.computeIfAbsent(assignmentId, k -> new HashSet<>());
        ContactAssignment ca = new ContactAssignment(assignmentId, contactId);
        if (!assSet.contains(ca)) {
            assSet.add(ca);
        }
        Set<ContactAssignment> contactSet = this.byContact.computeIfAbsent(contactId, k -> new HashSet<>());
        if (!contactSet.contains(ca)) {
            contactSet.add(ca);
        }
        return true;
    }

    /**
     * Unassign an assignment from a contact.
     *
     * @param assignmentId the id of the assignment to unassign
     * @param contactId    the id of the contact to unassign from
     */
    public boolean unallocate(String assignmentId, String contactId) {
        Set<ContactAssignment> assSet = this.byAssignment.get(assignmentId);
        if (assSet == null) {
            throw new AssignmentNotFoundException("This assignment does not have any contacts assigned.");
        }
        ContactAssignment toRemove = null;
        for (ContactAssignment ca : assSet) {
            if (ca.getContactId().equals(contactId)) {
                toRemove = ca;
                break;
            }
        }
        if (toRemove == null) {
            return false;
        }
        assSet.remove(toRemove);

        Set<ContactAssignment> contactSet = this.byContact.get(contactId);
        if (contactSet != null) {
            contactSet.remove(toRemove);
            if (contactSet.isEmpty()) {
                this.byContact.remove(contactId);
            }
        }
        if (assSet.isEmpty()) {
            this.byAssignment.remove(assignmentId);
        }
        return true;
    }

    private ContactAssignment find(String assignmentId, String contactId) {
        Set<ContactAssignment> assSet = this.byAssignment.get(assignmentId);
        if (assSet == null) {
            throw new AssignmentNotFoundException("This assignment does not have any contacts assigned.");
        }
        for (ContactAssignment ca : assSet) {
            if (ca.getContactId().equals(contactId)) {
                return ca;
            }
        }
        throw new ContactNotAssignedAssignmentException("Contact not assigned to assignment.");
    }

    /**
     * Marks the contact assignment for the given assignment and contact as
     * submitted.
     *
     * @param assignmentId the id of the assignment to mark as submitted
     * @param contactId    the id of the contact to mark as submitted
     */
    public void submit(String assignmentId, String contactId) {
        ContactAssignment ca = this.find(assignmentId, contactId);
        ca.markSubmitted();
    }

    /**
     * Grades the contact assignment for the given assignment and contact with a
     * score.
     *
     * @param assignmentId the id of the assignment to grade
     * @param contactId    the id of the contact to mark as graded
     * @param score        the score to assign to this contact assignment
     */
    public void grade(String assignmentId, String contactId, int score) {
        ContactAssignment ca = this.find(assignmentId, contactId);
        if (ca.isSubmitted()) {
            ca.grade(score);
        } else {
            throw new AssignmentNotSubmittedException("Contact has not submitted assignment.");
        }
    }

    public Set<ContactAssignment> getAssignmentsForContact(String contactId) {
        Objects.requireNonNull(contactId);
        return Collections.unmodifiableSet(this.byContact.getOrDefault(contactId, Collections.emptySet()));
    }

    public Set<ContactAssignment> getContactsForAssignment(String assignmentId) {
        return Collections.unmodifiableSet(this.byAssignment.getOrDefault(assignmentId, Collections.emptySet()));
    }
}

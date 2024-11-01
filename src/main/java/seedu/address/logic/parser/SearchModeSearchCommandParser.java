package seedu.address.logic.parser;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.person.predicates.PhoneNumberContainsKeywordPredicate;
import seedu.address.model.person.predicates.TelegramContainsKeywordsPredicate;
import seedu.address.model.role.Role;

/**
 * Parses input arguments and creates a new SearchModeSearchCommand object
 */
public class SearchModeSearchCommandParser implements Parser<SearchModeSearchCommand> {
    private static final Logger logger = Logger.getLogger(SearchModeSearchCommandParser.class.getName());
    @Override
    public SearchModeSearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_ROLE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TELEGRAM, PREFIX_ROLE);
        //for each prefix, if present, parse the value and create a predicate...
        // combine the predicates using AND
        // return the search command with the predicate
        // original predicate just show all
        Set<Predicate<Person>> predicates = new HashSet<>();
        // if a field is present, AND with the predicate for that field
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = argMultimap.getValue(PREFIX_NAME).get().trim();

            String[] nameKeywords = name.split("\\s+");

            Predicate<Person> namePred = new NameContainsKeywordsPredicate(
                    Arrays.stream(nameKeywords).toList());
            predicates.add(namePred);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phone = argMultimap.getValue(PREFIX_PHONE).get().trim();
            String[] phoneKeywords = phone.split("\\s+");

            Predicate<Person> phonePred = new PhoneNumberContainsKeywordPredicate(
                    Arrays.stream(phoneKeywords).toList());
            predicates.add(phonePred);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String email = argMultimap.getValue(PREFIX_EMAIL).get().trim();
            String[] emailKeywords = email.split("\\s+");

            Predicate<Person> emailPred = new EmailContainsKeywordsPredicate(
                    Arrays.stream(emailKeywords).toList());
            predicates.add(emailPred);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String address = argMultimap.getValue(PREFIX_ADDRESS).get().trim();
            String[] addressKeywords = address.split("\\s+");
            Predicate<Person> addressPred = new AddressContainsKeywordsPredicate(
                    Arrays.stream(addressKeywords).toList());

            predicates.add(addressPred);
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            String telegram = argMultimap.getValue(PREFIX_TELEGRAM).get().trim();
            String[] telegramKeywords = telegram.split("\\s+");
            Predicate<Person> telegramPred = new TelegramContainsKeywordsPredicate(
                    Arrays.stream(telegramKeywords).toList());
            predicates.add(telegramPred);
        }
        //role have to use separate predicate
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String roles = argMultimap.getValue(PREFIX_ROLE).get().trim();
            // map each word in String roles to a Role object


            Set<Role> roleSet = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));
            List<Role> roleList = roleSet.stream().collect(Collectors.toList());

            Predicate<Person> rolePred = new PersonIsRolePredicate(roleList);
            predicates.add(rolePred);
        }
        if (predicates.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SearchModeSearchCommand.MESSAGE_USAGE));
        }
        return new SearchModeSearchCommand(predicates);
    }


}